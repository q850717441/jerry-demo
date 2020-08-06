[easypoi结合spring-boot 快速使用](https://www.jianshu.com/p/6e1d962ff444)

## pom.xml
```xml
<!--excel操作-->
<!--如果有报错：class找不到，将3.0.3改成3.0.1-->
<dependency>
    <groupId>cn.afterturn</groupId>
    <artifactId>easypoi-base</artifactId>
    <version>3.0.3</version>
</dependency>
<dependency>
    <groupId>cn.afterturn</groupId>
    <artifactId>easypoi-web</artifactId>
    <version>3.0.3</version>
</dependency>
<dependency>
    <groupId>cn.afterturn</groupId>
    <artifactId>easypoi-annotation</artifactId>
    <version>3.0.3</version>
</dependency>
```
## 编写实体类

```java
@Excel(name = "编号", height = 20, width = 30, isImportField = "true_st")

@Excel(name = "状态", replace = {"禁用_1", "启用_0"}, isImportField = "true_st")

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Excel(name = "日期", width = 20, isImportField = "true_st")
private String createTime;

//例子：
@Excel(name = "发帖数", width = 20, isImportField = "true_st")
private Integer questionNum;//
```
## 导出工具

```java
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelUtil {
    public static void exportExcel(Workbook workbook, HttpServletResponse response, String name) {
        OutputStream out = null;
        try {
            // 写入Excel文件到前端
            if (null != workbook) {
                String excelName = name;
                String fileName = excelName + ".xls";
                fileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setContentType("application/x-download");
                response.setCharacterEncoding("UTF-8");
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                response.flushBuffer();
                out = response.getOutputStream();
                workbook.write(out);
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```
## 案例

```java
//controller
@GetMapping("/stats/Export")
public void statsExport(@RequestParam(required = false) String createTime,
                        HttpServletResponse response) {
    List<QaDTO> list=??;
    Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("社区互动统计", "文章"),
            QaDTO.class, list);
    ExcelUtil.exportExcel(workbook, response, "社区互动统计");
}
```