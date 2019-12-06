package com.semantic.city;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.tdb.store.Hash;
import com.semantic.models.Event;
import com.semantic.models.RealEstate;
import com.semantic.models.Restaurant;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.DoubleToIntFunction;

public class DataServices {

    static String serviceEndPoint = "http://104.154.49.211:3030/event/query";

    ArrayList<String> fetchZipCodes(String city){
        ArrayList<String> zipCode = new ArrayList<>();

        String zipCodeQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace/>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX ev: <http://www.w3.org/2001/xml-events/>\n" +
                "prefix event: <http://www.semanticweb.org/ontologies/2019/10/Event#>\n" +
                "prefix rg: <http://www.semanticweb.org/ontologies/2019/10/RestaurantGrocery#>\n" +
                "prefix re: <http://www.semanticweb.org/ontologies/2019/10/real_estate#>\n" +
                "\n" +
                "SELECT DISTINCT ?Zip\n" +
                "{\n" +
                "  {\n" +
                "    SERVICE <http://104.154.49.211:3030/event/sparql> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?Zip\n" +
                "      where\n" +
                "      {\n" +
                "        ?event event:eventCity \""+city+"\";\n" +
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
                "          ?restaurant rg:RGCity \""+city+"\";\n" +
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
                "          ?realestate re:realestateCity \""+city+"\";\n" +
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

    HashMap fetchCityResults(String zipCode){
        String query = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace/>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX ev: <http://www.w3.org/2001/xml-events/>\n" +
                "prefix event: <http://www.semanticweb.org/ontologies/2019/10/Event#>\n" +
                "prefix rg: <http://www.semanticweb.org/ontologies/2019/10/RestaurantGrocery#>\n" +
                "prefix re: <http://www.semanticweb.org/ontologies/2019/10/real_estate#>\n" +
                "\n" +
                "SELECT ?RestaurantName ?RestaurantAddress ?RestaurantPrice ?RestaurantRating ?RestaurantCategory ?EventName ?EventDate ?EventTime ?EventAddress ?EventMinPrice ?EventMaxPrice ?RealEstateName ?RealEstateAddress ?RealEstatePrice\n" +
                "{\n" +
                "   {\n" +
                "    SERVICE <http://104.154.49.211:3030/event/sparql> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?EventName ?EventDate ?EventMinPrice ?EventMaxPrice ?EventAddress ?EventTime\n" +
                "      where\n" +
                "      {\n" +
                "        ?event event:eventName ?EventName;\n" +
                "               event:eventDate ?EventDate;\n" +
                "               event:eventTime ?EventTime;\n" +
                "                    event:hasEventAddress ?y;\n" +
                "                    event:hasEventPrice ?EventPrice.\n" +
                "        ?y event:eventStreet ?EventAddress.\n" +
                "        ?EventPrice event:eventMinPrice ?EventMinPrice;\n" +
                "                    event:eventMaxPrice ?EventMaxPrice.\n" +
                "        ?y event:eventZip \""+zipCode+"\".        \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "    SERVICE <http://34.238.242.195:3030/rg/sparql> \n" +
                "    {\n" +
                "    SELECT DISTINCT ?RestaurantName ?RestaurantPrice ?RestaurantRating ?RestaurantAddress ?RestaurantCategory\n" +
                "    WHERE \n" +
                "    {\n" +
                "      ?restaurant rg:RGName ?RestaurantName;\n" +
                "                  rg:RGPrice ?RestaurantPrice;\n" +
                "                  rg:RGRatings ?RestaurantRating;\n" +
                "                  rg:RGCategory ?RestaurantCategory;\n" +
                "                  rg:hasRGaddress ?y.\n" +
                "        ?y rg:RGStreet ?RestaurantAddress.\n" +
                "      ?y rg:RGZip_code \""+zipCode+"\".\n" +
                "    }\n" +
                "    }\n" +
                "  }\n" +
                "  \n" +
                "  UNION\n" +
                "  {\n" +
                "    SERVICE <http://3.15.217.13:3030/realestate/query> \n" +
                "    {\n" +
                "      SELECT DISTINCT ?RealEstateName ?RealEstateAddress ?RealEstatePrice\n" +
                "      where\n" +
                "      {\n" +
                "          ?realestate re:realestatePropertyUrl ?RealEstateName;\n" +
                "                  re:realestateHasAddress ?y;\n" +
                "                  re:realestatePrice ?RealEstatePrice.\n" +
                "        ?y re:realestateStreet ?RealEstateAddress.\n" +
                "                  ?y re:realestateZip \""+zipCode+"\".\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceEndPoint,query);
        ResultSet results = q.execSelect();
        ArrayList<Event> eventList = new ArrayList<>();
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        ArrayList<RealEstate> realEstateList = new ArrayList<>();
        ArrayList<Event> finalEventList = new ArrayList<>();
        ArrayList<Restaurant> finalRestaurantList = new ArrayList<>();
        ArrayList<RealEstate> finalRealEstateList = new ArrayList<>();
        HashMap<String,ArrayList> finalResultMap = new HashMap<>();
        while(results.hasNext()){
            QuerySolution rows = results.nextSolution();
           // System.out.println(rows);
            Iterator<String> columns = rows.varNames();
            Event event = new Event();
            Restaurant restaurant = new Restaurant();
            RealEstate realEstate = new RealEstate();
            while (columns.hasNext()) {
                String columnName = columns.next().toString();
                RDFNode columnValue = rows.get(columnName);
                switch (columnName){
                    case "EventName": event.setName(columnValue.toString());
                    break;
                    case "EventDate": event.setDate(columnValue.toString().split("#")[0]);
                    break;
                    case "EventTime": event.setTime(columnValue.toString().split("#")[0]);
                    break;
                    case "EventMinPrice": event.setMinPrice("$"+columnValue.toString().split("\\^")[0]);
                    break;
                    case "EventMaxPrice": event.setMaxPrice("$"+columnValue.toString().split("\\^")[0]);
                    break;
                    case "EventAddress": event.setAddress(columnValue.toString());
                    break;
                    case "RestaurantPrice": restaurant.setPrice(columnValue.toString());
                    break;
                    case "RestaurantRating": restaurant.setRating(columnValue.toString().split("\\^")[0]);
                    break;
                    case "RestaurantName": restaurant.setName(columnValue.toString());
                    break;
                    case "RestaurantAddress": restaurant.setAddress(columnValue.toString());
                    break;
                    case "RestaurantCategory": restaurant.setCategory(columnValue.toString());
                    break;
                    case "RealEstateName": realEstate.setName(columnValue.toString());
                    break;
                    case "RealEstateAddress": realEstate.setStreet(columnValue.toString());
                    break;
                    case "RealEstatePrice": realEstate.setAmount("$" + columnValue.toString().split("\\^")[0]);
                    break;
                }

               // System.out.println(content.toString());
            }
           // System.out.println("end");
            eventList.add(event);
            restaurantList.add(restaurant);
            realEstateList.add(realEstate);
        }
        for (Event event: eventList){
            if ( !(event.getName() == null ) ) {
                finalEventList.add(event);
            }

        }
        for (Restaurant restaurant : restaurantList){
            if(!(restaurant.getName() == null)){
                finalRestaurantList.add(restaurant);
            }

        }
        for (RealEstate realEstate : realEstateList){
            if( !(realEstate.getName() == null)) {
                finalRealEstateList.add(realEstate);
            }
        }

        finalResultMap.put("Event",finalEventList);
        finalResultMap.put("Restaurant",finalRestaurantList);
        finalResultMap.put("RealEstate",finalRealEstateList);
        for (String key : finalResultMap.keySet()){
            System.out.println(finalResultMap.get(key));
        }

        return finalResultMap;

    }



    public static void main(String[] args) {
        DataServices ds = new DataServices();
        ds.fetchCityResults("Scottsdale");
   }
}
