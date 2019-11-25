package com.semantic.city;

import com.hp.hpl.jena.query.*;

import java.util.ArrayList;

public class DataServices {

    static String serviceEndPoint = "http://104.154.49.211:3030/event/query";
    ArrayList<String> fetchZipCodes(String city){
        ArrayList<String> zipCode = new ArrayList<>();
        String query =  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix owl: <http://www.w3.org/2002/07/owl#>" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "prefix RestaurantGrocery: <http://www.semanticweb.org/bhawanaprasad/ontologies/2019/10/RestaurantGrocery#> " +
                "prefix RestaurantGrocery1: <http://www.semanticweb.org/ontologies/2019/10/RestaurantGrocery#> " +
                "SELECT ?RestaurantName WHERE {?restaurant RestaurantGrocery1:RGName ?RestaurantName }";

        String query2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix owl: <http://www.w3.org/2002/07/owl#>" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "prefix RestaurantGrocery1: <http://www.semanticweb.org/ontologies/2019/10/RestaurantGrocery#>" +
                "SELECT DISTINCT ?Zip where {       ?restaurant RestaurantGrocery1:RGCity \"" + city + "\"; " +
                " RestaurantGrocery1:hasRGaddress ?y.    ?y RestaurantGrocery1:RGZip_code ?Zip.   }   ";

        String zipCodeQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace/>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX ev: <http://www.w3.org/2001/xml-events/>\n" +
                "prefix event: <http://www.semanticweb.org/ontologies/2019/10/Event#>\n" +
                "prefix rg: <http://www.semanticweb.org/ontologies/2019/10/RestaurantGrocery#>\n" +
                "prefix re: <http://www.semanticweb.org/ontologies/2019/10/real_estate#>\n" +
                "\n" +
                "SELECT ?Zip\n" +
                "{\n" +
                "  {\n" +
                "    SERVICE <http://104.154.49.211:3030/event/sparql> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?Zip\n" +
                "      where\n" +
                "      {\n" +
                "        ?event event:eventCity \"Scottsdale\";\n" +
                "                    event:hasEventAddress ?y.\n" +
                "        ?y event:eventZip ?Zip.        \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "    SERVICE <http://34.238.242.195:3030/rg/sparql> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?Zip\n" +
                "      where\n" +
                "      {\n" +
                "          ?restaurant rg:RGCity \"Scottsdale\";\n" +
                "                  rg:hasRGaddress ?y.\n" +
                "          ?y rg:RGZip_code ?Zip.        \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "    SERVICE <http://3.15.217.13:3030/realestate/query> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?Zip\n" +
                "      where\n" +
                "      {\n" +
                "          ?realestate re:realestateCity \"Scottsdale\";\n" +
                "                  re:realestateHasAddress ?y.\n" +
                "          ?y re:realestateZip ?Zip.        \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceEndPoint,zipCodeQuery);
        ResultSet results = q.execSelect();
        while(results.hasNext()){
            QuerySolution result = results.next();
            String zip = result.get("Zip").toString();
            zipCode.add(zip);
        }
        return zipCode;
    }

    public static void main(String[] args) {
        DataServices ds = new DataServices();
        ds.fetchZipCodes("Scottsdale");
    }
}
