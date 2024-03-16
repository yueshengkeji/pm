package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ContractWordModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractWordModelService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ContractWordModelController
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 13:26
 */
@RestController
@RequestMapping("/api/contractWordModel")
public class ContractWordModelController {
    @Autowired
    private ContractWordModelService contractWordModelService;

    @GetMapping("/list")
    public ResponseModel list(){
        return new ResponseModel(contractWordModelService.list());
    }

    @PostMapping("/insert")
    public ResponseModel insert(@RequestBody ContractWordModel contractWordModel){
        return new ResponseModel(contractWordModelService.insert(contractWordModel));
    }

    @PostMapping("/update")
    public ResponseModel update(@RequestBody ContractWordModel contractWordModel){
        return new ResponseModel(contractWordModelService.update(contractWordModel));
    }

    @PostMapping("/uploadWord")
    public ResponseModel uploadWord(@RequestParam("file") MultipartFile file) throws Exception {
        String content = "";
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".docx") && !fileName.endsWith(".doc")) {
            throw new Exception("错误的文件格式，请上传word");
        }
        InputStream in = file.getInputStream();
        if (fileName.endsWith(".docx")) {
            content = docxToHtml(in);
        } else {
            content = docToHtml(in);
        }
        return new ResponseModel(content);
    }

    private String docxToHtml(InputStream in) throws Exception {
        XWPFDocument document = new XWPFDocument(in);

        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        Map<String, String> picMap = new HashMap<>();
        XHTMLOptions options = XHTMLOptions.create();
        options.URIResolver(uri -> {
            if (uri.contains("/")) {
                int index = uri.lastIndexOf("/");
                picMap.put(uri.substring(index + 1), uri);
            }
            return uri;
        });
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);

        // 也可以使用字符数组流获取解析的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, baos, options);
        String content = baos.toString();
        baos.close();

        //图片暂不处理
//        List<XWPFPictureData> list = document.getAllPictures();
//        if (list != null) {
//            for (XWPFPictureData pic : list) {
//                byte[] data = pic.getData();
//                String fileName = pic.getFileName();
//                String res = fileClient.upload(data, (long) data.length, fileName);
//                String orignName = picMap.get(fileName);
//                if (StringUtils.isNotBlank(orignName)) {
//                    content = content.replace(orignName, res);
//                }
//            }
//        }

        return content;
    }

    private String docToHtml(InputStream in) throws Exception {
        HWPFDocument wordDocument = new HWPFDocument(in);
        WordToHtmlConverter wordToHtmlConverter = null;
        wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //图片暂不处理
//        wordToHtmlConverter.setPicturesManager((byte[] content, PictureType pictureType, String suggestedName,
//                                                float widthInches, float heightInches) -> {
//            return fileClient.upload(content, (long) content.length, suggestedName);
//        });

        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        OutputStream out = new ByteArrayOutputStream();

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "no");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        String content = out.toString();
        out.close();
        return content;
    }
}
