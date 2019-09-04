package com.six.tools;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickFixDictionary {
    private final FixMessageFormats format;
    private final Collection<FixField> fields = new LinkedList<>();


    public QuickFixDictionary(FixMessageFormats format) {
        this.format = format;

        loadAllFields();
    }

    public final String fixFormat() {
        return format.displayName();
    }

    public List<String> fieldNames() {
        return fields.stream().map(FixField::name).collect(Collectors.toList());
    }

    public List<FixField> fields() {
        return new ArrayList<>(fields);
    }

    private void loadAllFields() {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(format.dictionary())));

            NodeList fieldsList = doc.getElementsByTagName("fields");
            if(fieldsList.getLength() != 1) {
                throw new IllegalStateException("QFIX dictionary must have exactly 1 <fields> element");
            }

            NodeList fieldNodes = fieldsList.item(0).getChildNodes();
            IntStream.range(0, fieldNodes.getLength())
                    .mapToObj(fieldNodes::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(Element.class::cast)
                    .filter(elem -> StringUtils.equals(elem.getTagName(), "field"))
                    .filter(elem -> elem.hasAttribute("number") && elem.hasAttribute("name"))
                    .map(elem -> new FixField(Integer.parseInt(elem.getAttribute("number")), elem.getAttribute("name")))
                    .collect(Collectors.toCollection(() -> fields));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new IllegalStateException("Error parsing qfix dictionary", e);
        }
    }
}
