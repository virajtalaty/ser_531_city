package com.semantic.city;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class DataServices {
    static String serviceEndPoint = "http://104.154.49.211:3030/city/query";
    public void fetchResults(){
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
                "prefix event: <http://www.semanticweb.org/ontologies/2019/10/Event#>" +
                "SELECT DISTINCT ?Zip where {   {    ?restaurant RestaurantGrocery1:RGCity \"Tempe\"; " +
                " RestaurantGrocery1:hasRGaddress ?y.    ?y RestaurantGrocery1:RGZip_code ?Zip.   }  " +
                " UNION   {    ?restaurant event:eventCity \"Tempe\"; " +
                " event:hasEventAddress ?y.    ?y event:eventZip ?Zip.    }  }";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceEndPoint,query);
        ResultSet results = q.execSelect();
        ResultSetFormatter.out(System.out,results);

    }

    public static void main(String[] args) {
        DataServices ds = new DataServices();
        ds.fetchResults();
    }
}
