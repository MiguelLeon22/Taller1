/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Nano
 */
public class Utilidad {
    public static boolean crearXML(HashMap<Integer, FiguraGeometrica> mp, String ruta) {
       boolean t = false;
       try{
           Element figuraGeometrica = new Element("FigurasGeometrica");
           Document doc = new Document(figuraGeometrica);
           
           Iterator it = mp.entrySet().iterator();
           while (it.hasNext()) {
           
               Map.Entry<Integer, FiguraGeometrica> entry = (Map.Entry) it.next();
               int numf = entry.getKey();
               FiguraGeometrica objF = entry.getValue();
               LinkedList<Punto> LP = objF.getPuntos();
               
               Element figura = new Element("Figura");
               figura.setAttribute(new Attribute("Idfigura", String.valueOf(numf)));
               figura.setAttribute(new Attribute("NombreFigura", objF.getNombre()));
               figura.setAttribute(new Attribute("ColorBorde", objF.getColoBorde()));
               figura.setAttribute(new Attribute("ColorRelleno", objF.getColor()));
               figura.setAttribute(new Attribute("Stroke", String.valueOf(objF.getStroke())));
               
               for (int i = 0; i < LP.size(); i++) {
                   Punto get = LP.get(i);
                   figura.addContent(new Element("Punto" + i).
                           setAttribute(new Attribute("X", String.valueOf(get.getX()))).
                           setAttribute(new Attribute("Y", String.valueOf(get.getY()))));
               }
               doc.getRootElement().addContent(figura);
           }
           XMLOutputter xmlOutput = new XMLOutputter();
           xmlOutput.setFormat(Format.getPrettyFormat());
           xmlOutput.output(doc, new FileWriter(ruta + "\\FigurasGeometrica.xml"));
           t = true;
        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
        return t;
    }

/**
 * 
     * @param xd
    
     * @
 * @return, aquí retornamos el parámetro hm, que es el HashMap donce cargaremos las Figuras ya creadas.
 */
    
    public static HashMap cargarXML() {
        
        HashMap Mp = new HashMap();
        
//        Mp = null;
        
        
        
        try {
            File File = new File("FigurasGeometrica.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(File);
            System.out.println("Root element :" + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> listaFiguras = classElement.getChildren();
            

            for (int j = 0; j < listaFiguras.size(); j++) {
                LinkedList<Punto> lp = new LinkedList<>();
                
                Element figura = listaFiguras.get(j);
                
                int idFigura = Integer.parseInt(figura.getAttributeValue("Idfigura"));
                String nombre = figura.getAttributeValue("NombreFigura");
                String ColorBorde = figura.getAttributeValue("ColorBorde");
                String ColorRelleno = figura.getAttributeValue("ColorRelleno");
                double Stroke = Double.parseDouble(figura.getAttributeValue("Stroke"));
                
//                System.out.println("\n Current Element :" 
//                        + figura.getAttributeValue("Idfigura"));
                
                List<Element> listaPuntos = figura.getChildren();
                for (int i = 0; i < listaPuntos.size(); i++) {
                    
                    Element get = listaPuntos.get(i);
                    
                    double X = Double.parseDouble(get.getAttributeValue("X"));
                    double Y = Double.parseDouble(get.getAttributeValue("Y"));
                    
                    Punto punto = new Punto(X, Y);
                    lp.add(punto);
                    
//                    System.out.println("\npunto "+"X="+ get.getAttributeValue("X")+" Y="+get.getAttributeValue("Y"));
                    
                }
                
                FiguraGeometrica obje = new FiguraGeometrica(nombre, ColorRelleno, ColorBorde, Stroke, lp);
                Mp.put(idFigura, obje);
//                System.out.println(obje);
                
            }     
        } catch (Exception e) {
            System.out.println("error "+ e.toString());
        }
//        System.out.println(Mp);
        return Mp;
    }
}
