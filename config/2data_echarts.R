library(data.table)
library(tidyverse)
library(Biostrings)
library(stringi)
library(jsonlite)
args <- commandArgs(trailingOnly = TRUE)
SampleName <- "example_v3"
OutputPath <- file.path(SampleName)
Kit <- "ForenSeq DNA Signature"

# 读取DataTable
#STRaitRazorIO <- read.table(paste0("OUTPUT_DATA/STRaitRazorIO.tsv"), sep="\t", header=TRUE)
STRaitRazorIO <- read.table(args[1], sep="\t", header=TRUE)
# 读取AlleleSummary
#AlleleSummary <- read.table(paste0("OUTPUT_DATA/AlleleSummary.tsv"), sep="\t", header=TRUE)
AlleleSummary <- read.table(args[2], sep="\t", header=TRUE)
# 读取LocusSummary
#LocusSummary <- read.table(paste0("OUTPUT_DATA/LocusSummary.tsv"), sep="\t", header=TRUE)
LocusSummary <- read.table(args[3], sep="\t", header=TRUE)

#分步画图数据
#sraConfig <- read_csv("STRaitRazorAnalysisConfig.csv")
sraConfig <- read_csv(args[4])

#Import analysis config
                STRaitRazorAnalysisConfig <- as.data.frame(sraConfig) %>% 
                  filter(Kitid == Kit)
                Type <- select(STRaitRazorAnalysisConfig, Locus, Marker_Type)
                
                #Merge marker type
                DF <- left_join(STRaitRazorIO, Type)
                AS <- left_join(AlleleSummary, Type)
if(!dir.exists("echarts")) {
  dir.create("echarts")
}

#if(!dir.exists("figuresdata")) {
#  dir.create("figuresdata")
#}

#write_tsv(as.data.frame(STRaitRazorIO), paste0("figuresdata/STRaitRazorIO.tsv"))        
#write_tsv(as.data.frame(DF), paste0("figuresdata/byType_DF.tsv"))                     
#write_tsv(as.data.frame(AS), paste0("figuresdata/byType_AS.tsv"))    
#write_tsv(as.data.frame(LocusSummary), paste0("figuresdata/LocusSummary.tsv"))
#write_tsv(as.data.frame(AlleleSummary), paste0("figuresdata/AlleleSummary.tsv"))


####数据处理绘图
library(dplyr)

#write.csv(Type, "figuresdata/Locus_Marker_Type.csv", row.names = FALSE)# 分步画图snp,str索引

AlleleSummary_summarised <- AlleleSummary %>%
  select(-Allele) %>% # 移除 Allele 列
  group_by(Locus) %>% # 按 Locus 列分组
  summarise(across(everything(), sum, .names = "sum_{.col}")) %>% # 对其他所有列求和，并重命名
  arrange(sum_TotalReads) # 根据名为"sum"的列升序排列

# transposed是转置后的矩阵
 transposed <- t(AlleleSummary_summarised)

# 将第一行的每个元素用单引号包围
transposed[1, ] <- sapply(transposed[1, ], function(x) paste0("\"", x, "\""))

# 转换为数据框准备保存
transposed_df <- as.data.frame(transposed, stringsAsFactors = FALSE)

# 使用write.table保存transposed_df为TXT文件
# 不包含行名和列名，元素用逗号分隔
write.table(transposed_df, "echarts/data_for_LocusSummary.txt", sep = ",", col.names = FALSE, row.names = FALSE, quote = FALSE)
  
#write_tsv(as.data.frame(AlleleSummary_summarised), paste0("figuresdata/AlleleSummary_summarised.tsv"))

####柱状图数据处理
new_STRaitRazorIO <- STRaitRazorIO[,c("Locus", "Allele", "HaplotypeSum")]

# 为每个组创建一个矩阵列表
matrix_list <- lapply(split(new_STRaitRazorIO, new_STRaitRazorIO$Locus), function(df) {
  as.matrix(df[, -which(names(df) == "Locus")])
})

# 遍历matrix_list中的每个矩阵并排序
sorted_matrix_list <- lapply(matrix_list, function(matrix) {
  # 假设Allele是每个矩阵的第一列（如果是第二列，请相应调整）
  matrix[order(matrix[, "Allele"]), ]
})

# 确保当前工作目录中有newdata文件夹
if(!dir.exists("echarts/data_for_Electrofakogram")) {
  dir.create("echarts/data_for_Electrofakogram")
}

json_file_path <- "/Users/defuliu/research/openGUI/0/3Electrofakogram_副本.json";
json_data <- fromJSON(json_file_path);

# 遍历sorted_matrix_list中的每个元素
for(i in seq_along(sorted_matrix_list)) {
  m <- sorted_matrix_list[[i]]
  
  json_res <- json_data;
  
  # 检查是否是长度为2的数值向量
  if(is.numeric(m) && length(m) == 2) {
    json_res$xAxis$data <- list(m[1]);#设置x轴
    json_res$series$name <- "HaplotypeSum";
    json_res$series$type <- "bar";
    json_res$series$stack <- "total";
    json_res$series$barWidth <- "60%";
    data <- list(list,m[2]);
    json_res$series$data <- data;#设置数据
    # 直接保存为两行的文本文件
    file_name <- sprintf("echarts/data_for_Electrofakogram/%s.txt", names(sorted_matrix_list)[i])
    #writeLines(c(as.character(m[1]), as.character(m[2])), file_name)
    #writeLines(toJSON(json_res),file_name)
  } else {
    # 将矩阵转换为数据框
    df <- as.data.frame(m)
    colnames(df) <- c("Allele", "HaplotypeSum")
    
    # 创建一个唯一的标识符列
    df$id <- with(df, ave(Allele, Allele, FUN = seq_along))
    
    # 转换为宽格式，并去除id列
    new_wide_df <- df %>%
      spread(key = Allele, value = HaplotypeSum, fill = 0) %>%
      select(-id)
    
    # 构造文件名，包括路径
    file_name <- sprintf("echarts/data_for_Electrofakogram/%s.txt", names(sorted_matrix_list)[i])
    
    series <- data.frame(
      name = character(),
      type = character(),
      stack = character(),
      barWidth = character(),
      data = I(list())
    );
    
    json_res$xAxis$data <- matrix(names(new_wide_df), nrow = 1);
    for(j in 1:nrow(new_wide_df)) {
      
        mydata <- list(unlist(new_wide_df[j,], use.names = FALSE));
        series <- rbind(series, 
                        data.frame(
                           name = "HaplotypeSum",
                           type = "bar",
                           stack = "total",
                           barWidth = "60%",
                           data = I(mydata)))
    }
    #print(series)
    
    json_res$series <- series;
    
    
    # 保存为TXT文件，使用逗号作为分隔符
    #write.table(new_wide_df, file_name, row.names = FALSE, sep = ",", quote = FALSE)
  }
  myd = str_c('{"title": {"text": "',names(sorted_matrix_list)[i],'","left":"center"},
          "tooltip": {"trigger": ["axis"],"axisPointer": {"type":"shadow"}},
          "grid": {"left": "3%","right":"4%","bottom":"3%","containLabel":true},
          "xAxis":',toJSON(json_res$xAxis),',"yAxis":',toJSON(json_res$yAxis),',"series":',toJSON(json_res$series),'}');
  
  writeLines(myd,file_name)
}