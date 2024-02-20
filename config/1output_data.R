
library(data.table)
library(tidyverse)
library(Biostrings)
library(stringi)


convert2pre3 <- function(STRaitRazorIO, Config) {

  #Rename column headers   
  names(STRaitRazorIO) <- c("LocusAllele", "AlleleLength", "Haplotype", "HaplotypeCount", "HaplotypeCountRC")    
  
  #Breakout alleles, ditch allele length, and convert Amelogenin alleles
  STRaitRazorIO <- separate(data = STRaitRazorIO, into = c("Locus", "Allele"), col = LocusAllele, sep = ":") %>% 
    select(-(AlleleLength))
  
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(Real = (ifelse(str_detect(Allele, "-"), 0, 1))) %>% 
    filter(Real == 1) %>% 
    select(-(Real))
  
  STRaitRazorIO <- type_convert(STRaitRazorIO)
  
  #Enrich for STRs
  Type <- select(Config, Locus, Marker_Type)
  
  STRaitRazorIO <- left_join(STRaitRazorIO, Type, by = "Locus") %>% 
    select(Marker_Type, everything())
  
  STRaitRazorIO$Haplotype <- as.character(STRaitRazorIO$Haplotype)
  
  #Create reverse complement haplotypes (don't @ me I do realize there's a RC function)
  STRaitRazorIO$HaplotypeRC <- chartr("AGTC", "TCAG", Biostrings::reverse(STRaitRazorIO$Haplotype))
  
  #Remove variable
  rm(Type)
  
  return(STRaitRazorIO)
}

idcomphaps <- function(STRaitRazorIO, Config) {
  
  #Rename column headers  
  names(STRaitRazorIO) <- c("LocusAllele", "AlleleLength", "Haplotype", "HaplotypeRC", "HaplotypeCount")    
  
  #Breakout alleles, ditch allele length, and convert Amelogenin alleles
  STRaitRazorIO <- separate(data = STRaitRazorIO, into = c("Locus", "Allele"), col = LocusAllele, sep = ":") %>% 
    select(-(AlleleLength))
  
  X_Allele <- (STRaitRazorIO$Allele[STRaitRazorIO$Allele == "X"] <- 0)
  Y_Allele <- (STRaitRazorIO$Allele[STRaitRazorIO$Allele == "Y"] <- 6)

  #Enrich for STRs
  Type <- select(Config, Locus, Marker_Type)
  
  STRaitRazorIO <- left_join(STRaitRazorIO, Type, by = "Locus")
  
  #Merge locus and haplotype in case someone wants to do repeat region only
  STRaitRazorIO <- STRaitRazorIO %>% 
    unite(LH, Locus, Haplotype, remove = FALSE) %>% 
    unite(LHRC, Locus, HaplotypeRC, remove = FALSE)
  
  #Create tables for each haplotype
  LHRC_RD <- select(STRaitRazorIO, LHRC, HaplotypeCount) %>% 
    dplyr::rename(`HaplotypeCountRC` = `HaplotypeCount`, `LH` = `LHRC`)
  
  LH <- select(STRaitRazorIO, LH, HaplotypeCount)
  
  #Join two tables and remove temporary variables
  ljh <- left_join(LH, LHRC_RD, by = "LH")
  ljh[is.na(ljh)] <- 0
  STRaitRazorIO <- left_join(STRaitRazorIO, ljh, by = c("LH", "HaplotypeCount")) %>% 
    select(Marker_Type, Locus, Allele, Haplotype, HaplotypeRC, HaplotypeCount, HaplotypeCountRC)
  
  #Remove variable
  rm(Type)
  
  #Convert data types
  STRaitRazorIO <- type_convert(STRaitRazorIO)  
  
  return(STRaitRazorIO)
}



STRaitRazorFilter <- function(allsequences, SampleName, Kit, GlobalRDT = 2, mkallseq_version = "v3"){
  
  
  
  #Load STRait Razor result
  STRaitRazorIO <- allsequences
  attach(STRaitRazorIO)
  
  #Enrich for target strand and arrange data
  if (mkallseq_version == "v2s"){
    STRaitRazorIO <- idcomphaps(STRaitRazorIO, Config)
  }else{
    STRaitRazorIO <- convert2pre3(STRaitRazorIO, Config)
  }
  
  #Isolate desired strand and columns
  Motif <- select(Config, Locus, InternalMotif1, InternalMotif2, InternalMotif3, Repeat_Size)
  STRaitRazorIO <- left_join(STRaitRazorIO, Motif, by = "Locus")
  
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(Keep = (ifelse(str_detect(STRaitRazorIO$Haplotype, STRaitRazorIO$InternalMotif1), 1, 0) + ifelse(str_detect(STRaitRazorIO$Haplotype, STRaitRazorIO$InternalMotif2), 1, 0) + ifelse(str_detect(STRaitRazorIO$Haplotype, STRaitRazorIO$InternalMotif3), 1, 0))) %>% 
    mutate(KeepRC = (ifelse(str_detect(STRaitRazorIO$HaplotypeRC, STRaitRazorIO$InternalMotif1), 1, 0) + ifelse(str_detect(STRaitRazorIO$HaplotypeRC, STRaitRazorIO$InternalMotif2), 1, 0) + ifelse(str_detect(STRaitRazorIO$HaplotypeRC, STRaitRazorIO$InternalMotif3), 1, 0)))
  
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(FinalHaplotype = ifelse(STRaitRazorIO$Keep >= STRaitRazorIO$KeepRC, STRaitRazorIO$Haplotype, STRaitRazorIO$HaplotypeRC)) %>% 
    mutate(FinalHaplotype2 = paste0(Locus, "_", FinalHaplotype))
  
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(ContainsComp = ifelse((duplicated(STRaitRazorIO$FinalHaplotype2) | duplicated(STRaitRazorIO$FinalHaplotype2, fromLast = TRUE)), 1, 0))
  
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(DesiredStrand = ifelse(ContainsComp == 0, 1, ifelse(Keep >= KeepRC, 1, 0))) %>% 
    filter(DesiredStrand == 1) %>% 
    select(Locus, Allele, FinalHaplotype, HaplotypeCount, HaplotypeCountRC, Repeat_Size) %>% 
    dplyr::rename(`Haplotype` = `FinalHaplotype`)
  
  #Calculate locus depth, strand balance
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(SB = ifelse(HaplotypeCount == 0 | HaplotypeCountRC == 0, 0, HaplotypeCount/HaplotypeCountRC)) %>% 
    mutate(SBC = ifelse(SB > 1, 1/SB, SB)) %>%
    mutate(HaplotypeSum = (HaplotypeCount + HaplotypeCountRC))
  
  #Temporary merge locus and haplotype for DB
  HaplotypeDatabase <- HaplotypeDatabase %>% 
    unite(LH, Locus, Haplotype, remove = FALSE)
  STRaitRazorIO <- STRaitRazorIO %>% 
    unite(LH, Locus, Haplotype, remove = FALSE)
  
  #Merge with database
  HaplotypeDatabase <- select(HaplotypeDatabase, LH, Nomenclature, Allele_Char)
  STRaitRazorIO <- left_join(STRaitRazorIO, HaplotypeDatabase, by = "LH") %>% 
    select(-(LH))
  
  #Consolidate alleles for reporting
  STRaitRazorIO <- STRaitRazorIO %>% 
    mutate(FinalAllele = ifelse(is.na(Allele_Char), as.character(Allele), Allele_Char)) %>% 
    select(-(Allele_Char))
  
  #Combine Locus and CE state into nomenclature
  STRaitRazorIO$Nomenclature <- ifelse(is.na(STRaitRazorIO$Nomenclature), "", paste0(STRaitRazorIO$Locus," [CE ", as.character(STRaitRazorIO$FinalAllele),"]", STRaitRazorIO$Nomenclature)) 
  rm(HaplotypeDatabase)

  #Extract summary data
  LocusSummary <- summarize(dplyr::group_by(STRaitRazorIO, Locus), UniqueHaps = n(), TotalReads = sum(HaplotypeSum))
  AlleleSummary <- summarize(dplyr::group_by(STRaitRazorIO, Locus, Allele), UniqueHaps = n(), TotalReads = sum(HaplotypeSum))    
  
  #Apply global threshold
  STRaitRazorIO <- STRaitRazorIO %>% 
    filter(HaplotypeSum >= GlobalRDT)
  
  #Check if all haplotypes have been removed    
  if(nrow(STRaitRazorIO) == 0){
    
          #Sample subdirectories
      if (file.exists(file.path(Name))){
        ""
      }else{
        dir.create(file.path(Name), showWarnings = FALSE, recursive = TRUE)
      }
      
      OutputPath <- file.path(Name)
      
      #Save tables
      write_tsv(as.data.frame(relocate(select(STRaitRazorIO, -(Allele)), FinalAllele, .before = Haplotype)), paste0(OutputPath, "/" , "DataTable.tsv"))
      write_tsv(as.data.frame(AlleleSummary), paste0(OutputPath, "/",  "AlleleSummary.tsv"))
      write_tsv(as.data.frame(LocusSummary), paste0(OutputPath, "/", "LocusSummary.tsv"))
    
    #Return variables
    return(list(STRaitRazorIO, AlleleSummary, LocusSummary, SampleName, OutputPath))
    
  }else{ 
    #Relative allele proportion
    STRaitRazorIO <- left_join(STRaitRazorIO, LocusSummary, by = "Locus")
    
    STRaitRazorIO <- STRaitRazorIO %>% 
      mutate(RAP = (HaplotypeSum/TotalReads)) %>% 
      select(-(UniqueHaps), -(TotalReads), -(HaplotypeCount), -(HaplotypeCountRC), -(Repeat_Size))
    
    #Set thresholds
    Thresholds <- select(Config, Locus, HBT, RDT, SBT, RAPT)
    STRaitRazorIO <- left_join(STRaitRazorIO, Thresholds, by = "Locus")
    rm(Thresholds)
    
    #Create data frame for sub-threshold reads
    subThresh <- STRaitRazorIO %>% 
      filter(HaplotypeSum < RDT, SBC <= SBT, RAP < RAPT) %>% 
      select(-(RDT), -(SBT), -(RAPT), -(SBC), -(HBT))
    
    #Apply thresholds and rank haplotype abundance
    STRaitRazorIO <- filter(STRaitRazorIO, HaplotypeSum >= RDT, SBC >= SBT, RAP >= RAPT) %>% 
      select(-(RDT), -(SBT), -(RAPT), -(SBC)) %>% 
      dplyr::group_by(Locus) %>% 
      mutate(LocusRank = rank(-HaplotypeSum, ties.method = "first"))
    
    STRaitRazorIO <- STRaitRazorIO[with(STRaitRazorIO, order(Locus, LocusRank)),]
    
    #Error handling for low-template DNA samples
    if(nrow(STRaitRazorIO) == 0){
      
      #Sample subdirectories
      if (file.exists(file.path(Name))){
        ""
      }else{
        dir.create(file.path(Name), showWarnings = FALSE, recursive = TRUE)
      }
      
      OutputPath <- file.path(Name)
      
      #Save tables
      write_tsv(as.data.frame(relocate(select(STRaitRazorIO, -(Allele)), FinalAllele, .before = Haplotype)), paste0(OutputPath, "/" , "DataTable.tsv"))
      write_tsv(as.data.frame(AlleleSummary), paste0(OutputPath, "/",  "AlleleSummary.tsv"))
      write_tsv(as.data.frame(LocusSummary), paste0(OutputPath, "/", "LocusSummary.tsv"))
      write_tsv(as.data.frame(relocate(select(subThresh, -(Allele)), FinalAllele, .before = Haplotype)), paste0(OutputPath, "/", "subThresh.tsv"))
      
      #Return variables
      return(list(STRaitRazorIO, AlleleSummary, LocusSummary, SampleName, OutputPath))
      
    }else{

      #Create abundance ratio
      STRaitRazorIO <- left_join(STRaitRazorIO, select(mutate(left_join(dplyr::rename(aggregate(HaplotypeSum ~ Locus, STRaitRazorIO, function(x) sort(x, decreasing = T)[1]), First = HaplotypeSum), dplyr::rename(aggregate(HaplotypeSum ~ Locus, STRaitRazorIO, function(x) sort(x, decreasing = T)[2]), Second = HaplotypeSum), by = "Locus"), AR = Second/First), Locus, AR), by = "Locus")
      
      #Single haplotype adjustment
      STRaitRazorIO$AR <- ifelse(is.na(STRaitRazorIO$AR), 0, STRaitRazorIO$AR)
      
      #Auto call alleles
      STRaitRazorIO$AutoCall <- ifelse(STRaitRazorIO$AR >= STRaitRazorIO$HBT & STRaitRazorIO$LocusRank <= 2, "a", ifelse(STRaitRazorIO$AR < STRaitRazorIO$HBT, ifelse(STRaitRazorIO$LocusRank == 1, "a", ""), ""))
      
      #Clean up heterozygote balance
      STRaitRazorIO$AR <- ifelse(STRaitRazorIO$AutoCall == "a" & STRaitRazorIO$LocusRank <= 2 & STRaitRazorIO$AR >= as.numeric(STRaitRazorIO$HBT), STRaitRazorIO$AR, "")
      
      #Spread data frame to determine balance direction
      TempBalance <- filter(select(STRaitRazorIO, Locus, FinalAllele, LocusRank, AutoCall), AutoCall == "a") %>% 
        spread(LocusRank, FinalAllele)
      
      #This accounts for homozygous only profiles (i.e., low-template DNA)
      if(length(TempBalance) == 3){
        TempBalance$AB <- 1
      }else{
        TempBalance$AB <- ifelse(is.na(TempBalance$`2`), "", ifelse(is.na(as.numeric(TempBalance$`1`)), ifelse(stri_cmp_lt(TempBalance$`1`, TempBalance$`2`), 1, 0), ifelse(as.numeric(TempBalance$`1`) < as.numeric(TempBalance$`2`), 1, 0)))
      }
      
      STRaitRazorIO <- left_join(STRaitRazorIO, select(TempBalance, Locus, AB), by = "Locus")
      STRaitRazorIO$AB <- ifelse(STRaitRazorIO$AR > 0, STRaitRazorIO$AB, "")
      
      #Calculate heterozygote balance
      STRaitRazorIO <- STRaitRazorIO %>% 
        type_convert() %>% 
        mutate(HB = ifelse(AB == 0, (1/as.numeric(AR)), ifelse(AB == 1, AR, ""))) %>% 
        select(-(AB), -(HBT))
      
      #Convert final allele to character to account for SNP-less SNP data
      STRaitRazorIO$FinalAllele <- as.character(STRaitRazorIO$FinalAllele)
      
      #Sample subdirectories
      if (file.exists(file.path(Name))){
        ""
      }else{
        dir.create(file.path(Name), showWarnings = FALSE, recursive = TRUE)
      }
      
      OutputPath <- file.path(Name)
      
      #Save tables
      write_tsv(as.data.frame(relocate(select(STRaitRazorIO, -(Allele)), FinalAllele, .before = Haplotype)), paste0(OutputPath, "/" , "DataTable.tsv"))
      write_tsv(as.data.frame(AlleleSummary), paste0(OutputPath, "/",  "AlleleSummary.tsv"))
      write_tsv(as.data.frame(LocusSummary), paste0(OutputPath, "/", "LocusSummary.tsv"))
      write_tsv(as.data.frame(relocate(select(subThresh, -(Allele)), FinalAllele, .before = Haplotype)), paste0(OutputPath, "/", "subThresh.tsv"))
      
      #Return variables
      return(list(STRaitRazorIO, AlleleSummary, LocusSummary, SampleName, OutputPath))                          
      
    }
  }}

################################################################################################

args <- commandArgs(trailingOnly = TRUE)
SampleName <- "example_v3"
Name <- "OUTPUT_DATA"
STRaitRazorIO <- read_delim(args[1], "\t", escape_double = FALSE, col_names = FALSE, trim_ws = TRUE)

GlobalRDT <- 10
allsequences <- STRaitRazorIO
Kit <- "ForenSeq DNA Signature"


  #Identify paths and read in database variables
  
  Config <- read_csv(args[2]) %>% 
    filter(Kitid == Kit)
  HaplotypeDatabase <- read_csv(args[3]) %>% 
    filter(Kitid == Kit)
  #ResultsDir <- paste0(file.path("data", DatabasePaths[[4, 2]]), "/", Kit)
  ResultsDir <- paste0(file.path("AnalysisOutput"), "/", "data")
  OutputDir <- paste0("STRaitRazoR_AnalysisResults")


FilterResults <- STRaitRazorFilter(allsequences = STRaitRazorIO, SampleName = SampleName, Kit = Kit, GlobalRDT = GlobalRDT, mkallseq_version = "v3")

STRaitRazorIO <- data.frame(FilterResults[1]) %>% 
            mutate(SampleName = SampleName) %>% 
            select(SampleName, everything())

OutputPath <- file.path(Name)
write_tsv(as.data.frame(STRaitRazorIO), paste0(OutputPath, "/","STRaitRazorIO.tsv"))           
