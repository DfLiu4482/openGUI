library(readr)

library(tidyverse)


#绘图颜色调用函数
Palette272 <- c('#a6cee3','#1f78b4','#b2df8a','#33a02c','#fb9a99','#e31a1c','#fdbf6f','#ff7f00','#cab2d6','#6a3d9a','#ffff99','#b15928', "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4", "#f9f9f9", "#2a2526", "#e5e6e7", "#6b6a6b", "#a6a4a4")



########################STRaitRazorFigures 函数########################
#生成和保存几种不同类型的图形

  
  STRaitRazorFigures <- function(STRaitRazorIO, AlleleSummary, LocusSummary, GlobalRDT = 2, SampleName, OutputPath){
  #Generate color palette
  #这两行代码生成一个随机颜色调色板。colorCount是STRaitRazorIO数据框中LocusRank列的最大值。randomColor是除去灰色系之外的所有颜色。
  colorCount = max(STRaitRazorIO$LocusRank)   
  randomColor = grDevices::colors()[grep('gr(a|e)y', grDevices::colors(), invert = T)]
  
  
  #Plot sequence-based bar plot
  #接下来的代码块绘制了一个基于序列的条形图，名为Electrofakogram。它使用条件判断来决定颜色的选择：如果colorCount大于272，将使用随机颜色调色板。否则，将使用预定义的Palette272颜色调色板。
  if(colorCount > 272){
    Electrofakogram <- ggplot(STRaitRazorIO, aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus, scales = "free_x") + theme_classic() + scale_fill_manual(values = sample(randomColor,colorCount)) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))
    
##    ##这行代码使用 ggplot2包生成一个名为Electrofakogram的基于序列的条形图。以下是每个组件的具体解释：

#    ggplot基本设置:
#      ggplot(STRaitRazorIO, aes(Allele, HaplotypeSum, fill = as.factor(LocusRank)))这是绘图的起始命令，它设置了数据框STRaitRazorIO和图的美学映射（aes）:

#      Allele用于x轴。
#      HaplotypeSum用于y轴。
#      LocusRank转换为因子用于填充颜色。
#      geom_bar:

#    geom_bar(stat = "identity", show.legend = FALSE)
#    这添加了条形图层，stat="identity"指明数据中的y值将直接用于条形的高度，不需要进行统计变换。show.legend =FALSE表示不显示图例。

#   facet_wrap:

#   facet_wrap(~Locus, scales = "free_x")
#   这使图形按照Locus变量分面显示，并允许每个面的x轴自由缩放。

#   theme_classic:

#   theme_classic()
#   这设置了图形的主题为经典主题，提供了一个简洁的视觉布局。

#   scale_fill_manual:

#   scale_fill_manual(values = sample(randomColor, colorCount))
#   这定义了条形的填充颜色。它从randomColor中随机选择colorCount数量的颜色。

#   theme:

#   theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5))
#   这一行详细自定义了图形的各个元素，包括:

#   清除x轴的文本、线条和刻度。
#   设置标题和x轴标题的水平和垂直对齐方式。
#   labs:

#   labs(title = paste0(SampleName, " Sequence-based Barplot"), y = "Read Depth", x = "Alleles")
#   这设置了图形的标题、y轴和x轴标签。标题是动态生成的，包含SampleName。

#   geom_text:

#   geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2)
#   这添加了文本标签，显示在每个条形的底部，显示等位基因的值，文本角度为90度。

#   geom_hline:

#   geom_hline(yintercept = 0)
#   这添加了一个y轴截距为0的水平线。

#   scale_y_continuous:

#   scale_y_continuous(expand = c(0.15, 0))
#   这设置了y轴的连续缩放，扩展了图形的范围以避免条形图与轴线的重叠。

##  综上所述，这段代码生成了一个复杂的条形图，显示了每个Locus的Allele和HaplotypeSum，并通过颜色区分了LocusRank。
    
  }else{
    Electrofakogram <- ggplot(STRaitRazorIO, aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus, scales = "free_x") + theme_classic() + scale_fill_manual(values = Palette272) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))  

#  与上区别仅使用调色板方案不同  一个是cale_fill_manual(values = sample(randomColor,colorCount))，一个是scale_fill_manual(values = Palette272)
  }

  
  ggsave(paste0(OutputPath, "/", SampleName, "_SB_Electrofakogram.jpg"), plot = Electrofakogram, width = 24, height = 24, dpi = 320)
  
##  filename: 图片的保存路径和文件名。使用 paste0 函数动态构建，包括指定的输出路径 (OutputPath)，样本名 (SampleName)，和固定的文件名后缀 "_SB_Electrofakogram.jpg"。这意味着每个样本的文件名都是唯一的，基于它的样本名。

##plot: 指定要保存的图形对象，这里是 Electrofakogram。

##width 和 height: 图片的宽度和高度，单位是英寸。这里都设置为24，意味着生成的图片将是一个大的正方形图。

##dpi (dots per inch): 图像的分辨率。这里设置为320，这是一个相对较高的分辨率，意味着图像将具有较好的细节清晰度。
  
  #Plot locus summary
  LocusSummaryPlot <- ggplot(LocusSummary) + geom_bar(aes(reorder(Locus, TotalReads), TotalReads), stat = "identity", show.legend = FALSE) + geom_point(aes(reorder(Locus, TotalReads), UniqueHaps)) + theme_classic() + theme(plot.title = element_text(hjust = 0.5), axis.text.x = element_text(hjust = 0.5, vjust = 0.5)) + labs(title = paste0(SampleName," Locus Summary"), x = "Locus", y = "Total Reads") + coord_flip() + scale_y_continuous(expand = c(0, 0))

##这段代码生成了一个名为 LocusSummaryPlot 的位点总结图，使用 ggplot2 包进行绘图。以下是每个组件的具体解释：

#    ggplot基本设置:

#   ggplot(LocusSummary)
#   这是绘图的起始命令，它设置了数据框 LocusSummary 作为数据源。

#   geom_bar:

#   geom_bar(aes(reorder(Locus, TotalReads), TotalReads), stat = "identity", show.legend = FALSE)
#   这添加了条形图层，用于显示每个位点的总读取次数 (TotalReads)：

#   aes(reorder(Locus, TotalReads), TotalReads)：这设置了条形图的x轴和y轴。reorder(Locus, TotalReads) 根据 TotalReads 的数量对 #     Locus 进行排序，确保条形图按读取次数排序。
#     stat = "identity"：这指明数据中的 y 值将直接用于条形的高度，不需要进行统计变换。
#     show.legend = FALSE：表示不显示图例。

#   geom_point:

#   geom_point(aes(reorder(Locus, TotalReads), UniqueHaps))
#   这添加了点图层，用于在相同的x轴位置显示 UniqueHaps 的值，即每个位点的独特重复类型数量。

#   theme_classic:

#   theme_classic()
#   这设置了图形的主题为经典主题，提供了一个简洁的视觉布局。

#   theme:

#   theme(plot.title = element_text(hjust = 0.5), axis.text.x = element_text(hjust = 0.5, vjust = 0.5))
#   这一行详细自定义了图形的各个元素，包括标题的水平对齐和x轴文本的对齐方式。

#   labs:

#   labs(title = paste0(SampleName," Locus Summary"), x = "Locus", y = "Total Reads")
#   这设置了图形的标题、x轴和y轴标签。标题是动态生成的，包含 SampleName。

#   coord_flip:

#   coord_flip()
#   这使图形的坐标轴翻转，即将x轴和y轴互换，使得条形图水平展示。

#   scale_y_continuous:

#   scale_y_continuous(expand = c(0, 0))
#   这设置了y轴的连续缩放，此处不扩展图形的范围，保持原有比例。

#   综上所述，这段代码生成了一个位点总结图，以条形图和点图的形式同时显示每个位点的 TotalReads 和  UniqueHaps。通过使用经典主题、自定义标签和标题，以及坐标轴翻转，这个图形提供了对位点数据的清晰可视化表示。

  
  ggsave(paste0(OutputPath, "/",SampleName, "_LocusSummaryPlot.jpg"), plot = LocusSummaryPlot, width = 9, height = 24, dpi = 320)
  
  #Plot length-based bar plot
  ElectroFakogram_LB <- ggplot(filter(AlleleSummary, TotalReads >= GlobalRDT), aes(Allele, TotalReads)) + geom_bar(stat = "identity") + facet_wrap(~Locus, scales = "free_x") + theme_classic() + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Length-based Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(TotalReads)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))
  
##  这段代码生成了一个名为 ElectroFakogram_LB 的基于长度的条形图，使用 ggplot2 包进行绘图。以下是每个组件的具体解释：

#   ggplot基本设置:

#   ggplot(filter(AlleleSummary, TotalReads >= GlobalRDT), aes(Allele, TotalReads))
#   这是绘图的起始命令，它设置了经过筛选的 AlleleSummary 数据框作为数据源。筛选条件是 TotalReads 大于等于全局读取深度阈值（GlobalRDT）。

#   geom_bar:

#   geom_bar(stat = "identity")
#   这添加了条形图层，stat = "identity" 指明数据中的 y 值将直接用于条形的高度。

#   facet_wrap:

#   facet_wrap(~Locus, scales = "free_x")
#   这使图形按照 Locus 变量分面显示，并允许每个面的x轴自由缩放。

#   theme_classic:

#   theme_classic()
#   这设置了图形的主题为经典主题，提供了一个简洁的视觉布局。

#   theme:

#   theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5))
#   这一行详细自定义了图形的各个元素，包括清除x轴的文本、线条和刻度，并设置标题和x轴标题的对齐方式。

#   labs:

#   labs(title = paste0(SampleName, " Length-based Barplot"), y = "Read Depth", x = "Alleles")
#   这设置了图形的标题、y轴和x轴标签。标题是动态生成的，包含 SampleName。

#   geom_text:

#   geom_text(aes(x = Allele, y = (0-(max(TotalReads)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2)
#   这添加了文本标签，显示在每个条形的底部，显示等位基因的值，文本角度为90度。

#   geom_hline:

#   geom_hline(yintercept = 0)
#   这添加了一个y轴截距为0的水平线。

#   scale_y_continuous:

#   scale_y_continuous(expand = c(0.15, 0))
#   这设置了y轴的连续缩放，扩展了图形的范围以避免条形图与轴线的重叠。

#   综上所述，这段代码生成了一个基于长度的条形图，显示了满足 GlobalRDT 阈值的每个 Locus 的 Allele 和 TotalReads。通过使用经典主题、自定义标签和标题，以及坐标轴的详细调整，这个图形提供了对读取深度数据的清晰可视化表示。
  
  ggsave(paste0(OutputPath, "/", SampleName, "_LB_ElectroFakogram.jpg"), plot = ElectroFakogram_LB, width = 24, height = 24, dpi = 320)
}



########################STRaitRazorFigures_byType 函数########################
###########按类型保存图形

STRaitRazorFigures_byType <- function(STRaitRazorIO, AlleleSummary, LocusSummary, GlobalRDT = 2, SampleName, OutputPath){
  
  #Generate color palette
  colorCount = max(STRaitRazorIO$LocusRank)
  randomColor = grDevices::colors()[grep('gr(a|e)y', grDevices::colors(), invert = T)]
  
  #Save STR figures 
  if(colorCount > 272){
    Electrofakogram <- ggplot(filter(STRaitRazorIO, Marker_Type == "STR"), aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus, scales = "free_x") + theme_classic() + scale_fill_manual(values = sample(randomColor,colorCount)) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based STR Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))
    
##  byType部分Electrofakogram代码主要绘制了 Electrofakogram 图形，但在数据筛选和图表标题方面有细微差别。

#   数据筛选:

#    此代码在创建图形之前对 STRaitRazorIO 数据进行了筛选，只包括 Marker_Type 等于 "STR" 的行：

#     filter(STRaitRazorIO, Marker_Type == "STR")
#     这意味着图形只包含标记类型为 "STR" 的数据。

#      STRaitRazorFigures 函数代码没有应用这样的筛选，它使用 STRaitRazorIO 数据的全部内容进行绘图。

#   图表标题:

#   此段代码的图表标题特别指出这是一个基于 "STR" 的序列条形图：
#   labs(title = paste0(SampleName, " Sequence-based STR Barplot"))

#   STRaitRazorFigures 函数代码的图表标题更一般，没有特别指出 "STR"：
#   labs(title = paste0(SampleName, " Sequence-based Barplot"))

#   其他部分，包括图形的美学设置（颜色填充、轴标签、文本标签等）和主题设置，在两段代码中都是一样的。    
    
  }else{
    Electrofakogram <- ggplot(filter(STRaitRazorIO, Marker_Type == "STR"), aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus, scales = "free_x") + theme_classic() + scale_fill_manual(values = Palette272) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based STR Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))      
  }
  
  ggsave(paste0(OutputPath, "/", SampleName, "_SB_STR_Electrofakogram.jpg"), plot = Electrofakogram, width = 12, height = 12, dpi = 320)
  
  LocusSummaryPlot <- ggplot(LocusSummary) + geom_bar(aes(reorder(Locus, TotalReads), TotalReads), stat = "identity", show.legend = FALSE) + geom_point(aes(reorder(Locus, TotalReads), UniqueHaps)) + theme_classic() + theme(plot.title = element_text(hjust = 0.5), axis.text.x = element_text(hjust = 0.5, vjust = 0.5), axis.text.y = element_text(size = 3)) + labs(title = paste0(SampleName," Locus Summary"), x = "Locus", y = "Total Reads") + coord_flip() + scale_y_continuous(expand = c(0, 0))
  
##    与STRaitRazorFigures 函数LocusSummaryPlot代码对比
#     这两段代码都生成了名为 LocusSummaryPlot 的位点总结图，但它们在y轴文本的大小设置上有所不同：

#   此代码:在 theme 函数中，第一段代码设置了y轴文本的大小为3：

#   theme(..., axis.text.y = element_text(size = 3))
#   这意味着y轴的文本标签将被设置为相对较小的大小（大小为3）。

#   STRaitRazorFigures 函数LocusSummaryPlot代码没有包含对y轴文本大小的设置：

#   theme(... )
#   这意味着y轴的文本标签将使用 theme_classic() 的默认大小。
#   这是两段代码之间唯一的区别。其他所有设置，包括条形图和点图的绘制、坐标轴的翻转、主题的应用和标签的设置等，都是相同的。

#   总结来说，两段代码的主要区别在于第一段代码中y轴文本的大小被明确设置为3，而第二段代码则使用默认的文本大小。这种差异影响的是图表的视觉呈现，特别是y轴标签的可读性。
  
  
  ggsave(paste0(OutputPath, "/", SampleName, "_LocusSummaryPlot.jpg"), plot = LocusSummaryPlot, width = 9, height = 12, dpi = 320)
  
  ElectroFakogram_LB <- ggplot(filter(AlleleSummary, TotalReads >= GlobalRDT, Marker_Type == "STR"), aes(Allele, TotalReads)) + geom_bar(stat = "identity") + facet_wrap(~Locus, scales = "free_x") + theme_classic() + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Length-based STR Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(TotalReads)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15, 0))

##    与STRaitRazorFigures 函数ElectroFakogram_LB代码对比  
#    这两段代码都用于生成名为 ElectroFakogram_LB 的基于长度的条形图，但在数据筛选条件上有区别：

#    此代码代码:

#    在创建图形之前对 AlleleSummary 数据进行了两层筛选，确保只包括 TotalReads 大于等于 GlobalRDT 并且 Marker_Type 等于 "STR" 的行：

#    filter(AlleleSummary, TotalReads >= GlobalRDT, Marker_Type == "STR")
#    这意味着图形只包含标记类型为 "STR" 且满足读取深度阈值的数据。

#    图表标题特别指出这是一个基于 "STR" 的长度条形图：

#    labs(title = paste0(SampleName, " Length-based STR Barplot"))

#    STRaitRazorFigures 函数ElectroFakogram_LB代码:
#    对 AlleleSummary 数据只进行了一层筛选，确保包括的行是 TotalReads 大于等于 GlobalRDT：

#    filter(AlleleSummary, TotalReads >= GlobalRDT)
#    这意味着图形包含了所有满足读取深度阈值的数据，不考虑 Marker_Type。

#    图表标题更一般，没有特别指出 "STR"：

#    labs(title = paste0(SampleName, " Length-based Barplot"))
#    其他部分，包括图形的美学设置（颜色填充、轴标签、文本标签等）和主题设置，在两段代码中都是一样的。

#    总结来说，两段代码的主要区别在于第一段代码专门针对标记类型为 "STR" 且满足读取深度阈值的数据进行绘图，并在图表标题中明确表示了这一点，而第二段代码则对满足读取深度阈值的所有数据进行绘图。
  
  
  
  
  ggsave(paste0(OutputPath, "/", SampleName, "_LB_STR_ElectroFakogram.jpg"), plot = ElectroFakogram_LB, width = 12, height = 12, dpi = 320)
  
  #Save SNP figures 
  
  #Sequence-based bar plot
  if(colorCount > 272){
    Electrofakogram <- ggplot(filter(STRaitRazorIO, Marker_Type == "SNP"), aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus) + theme_classic() + scale_fill_manual(values = sample(randomColor,colorCount)) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based SNP Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15,0), limits = c(-20, 20))

##  与本函数中223行Electrofakogram <- ggplot(filter(STRaitRazorIO, Marker_Type == "STR")...区别    
#      这两段代码都用于创建名为 Electrofakogram 的图形，但它们在几个方面有所不同：

#  数据筛选条件:

#  此代码只包括 Marker_Type 等于 "SNP" 的数据：

#  filter(STRaitRazorIO, Marker_Type == "SNP")

#  STR代码只包括 Marker_Type 等于 "STR" 的数据：

#  filter(STRaitRazorIO, Marker_Type == "STR")

#  facet_wrap:

#  此代码中，facet_wrap 没有设置 scales 参数，这意味着所有面的x轴和y轴将有相同的缩放：

#  facet_wrap(~Locus)

#  STR代码中，facet_wrap 使用了 scales = "free_x" 参数，允许每个面的x轴自由缩放：

#  facet_wrap(~Locus, scales = "free_x")

#  图表标题:

#  此代码的图表标题特定于 "SNP"：

#  labs(title = paste0(SampleName, " Sequence-based SNP Barplot"))

#  STR代码的图表标题特定于 "STR"：

#  labs(title = paste0(SampleName, " Sequence-based STR Barplot"))
#  scale_y_continuous:

#  此代码设置了y轴的范围限制为 -20 到 20：

#  scale_y_continuous(expand = c(0.15, 0), limits = c(-20, 20))

#  STR代码没有设置y轴的范围限制：

#  scale_y_continuous(expand = c(0.15, 0))
#  这些区别反映了两段代码的图形虽然在结构上相似，但它们针对的数据集、x轴的缩放方式、图表标题的具体内容以及y轴的限制范围都有所不同。
  
  }else{
    Electrofakogram <- ggplot(filter(STRaitRazorIO, Marker_Type == "SNP"), aes(Allele, HaplotypeSum, fill = as.factor(LocusRank))) + geom_bar(stat = "identity", show.legend = FALSE) + facet_wrap(~Locus) + theme_classic() + scale_fill_manual(values = Palette272) + theme(axis.text.x = element_blank(), axis.line.x = element_blank(), axis.ticks.x = element_blank(), plot.title = element_text(hjust = 0.5), axis.title.x = element_text(vjust = 0.5, hjust = 0.5)) + labs(title = paste0(SampleName, " Sequence-based SNP Barplot"), y = "Read Depth", x = "Alleles") + geom_text(aes(x = Allele, y = (0-(max(HaplotypeSum)/10)), label = round(Allele, 1), angle = 90, hjust = 0.5, vjust = 0.5), size = 2) + geom_hline(yintercept = 0) + scale_y_continuous(expand = c(0.15,0)) + scale_x_continuous(limits = c(-20, 20))      
  }
  
  ggsave(paste0(OutputPath, "/", SampleName, "_SB_SNP_Electrofakogram.jpg"), plot = Electrofakogram, width = 24, height = 24, dpi = 320)
  
}
 
#############################################################################
###########主函数
args <- commandArgs(trailingOnly = TRUE)
sepByType <- FALSE ##选择哪种画图方式，TRUE:sepByType方式四张图，FALSE:另一个方式不按类型作图三张图

#sraConfig <- read_csv("STRaitRazorAnalysisConfig.csv")#sep作图方式时使用,原工程地址为"db/db/STRaitRazorAnalysisConfig.csv"
sraConfig <- read_csv(args[1])
Kit <- "ForenSeq DNA Signature"#试剂盒名
SampleName <- "example_v3"

# 读取数据
#作图所需的数据，由原代码中STRaitRazorFilter函数生成

#STRaitRazorIO <- read_tsv("OUTPUT_DATA/STRaitRazorIO.tsv")
STRaitRazorIO <- read_tsv(args[2])
#AlleleSummary <- read_tsv("OUTPUT_DATA/AlleleSummary.tsv")
AlleleSummary <- read_tsv(args[3])
#LocusSummary <- read_tsv("OUTPUT_DATA/LocusSummary.tsv")
LocusSummary <- read_tsv(args[4])

GlobalRDT <- 10
OutputPath1 <- file.path("allpic") #不按类型作图保存位置
OutputPath2 <- file.path("allpic") #sepByType方式保存位置

if(sepByType == TRUE){
                OutputPath <- OutputPath2
                
                #Import analysis config
                STRaitRazorAnalysisConfig <- as.data.frame(sraConfig) %>% 
                  filter(Kitid == Kit)
                Type <- select(STRaitRazorAnalysisConfig, Locus, Marker_Type)
                
                #Merge marker type
                DF <- left_join(STRaitRazorIO, Type)
                AS <- left_join(AlleleSummary, Type)
                
                #Plot data by type
                STRaitRazorFigures_byType(DF, AS, LocusSummary, GlobalRDT = GlobalRDT, SampleName, OutputPath)
                
                }else{
                OutputPath <- OutputPath1
                
                STRaitRazorFigures(STRaitRazorIO, AlleleSummary, LocusSummary, GlobalRDT = GlobalRDT, SampleName, OutputPath)
              } 
    
