package com.semantic.city;

import com.semantic.models.City;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping({"/", "/hello"})
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @PostMapping("/getZip")
    public String getZip(HttpServletRequest request, @RequestParam String citySelected, Model model)
    {
        DataServices ds = new DataServices();
        ds.fetchZipCodes(citySelected);
        List<String> zipCodes = ds.fetchZipCodes(citySelected);
        request.getSession().setAttribute("zipCodes", zipCodes);
        model.addAttribute("zipCodes", zipCodes);
        model.addAttribute("citySelected", citySelected);
        return "hello";
    }

    @GetMapping("/getDetails")
    public String getDetails(HttpServletRequest request, @RequestParam String citySelected,
                             @RequestParam String zipSelected, Model model)
    {
        List<String> zipCodes = (List<String>)request.getSession().getAttribute("empList");
        model.addAttribute("zipCodes", zipCodes);
        model.addAttribute("citySelected", citySelected);
        model.addAttribute("zipSelected", zipSelected);

        City newCity = new City();
        List<String> eventList = new ArrayList<>();
        List<String> realEstateList = new ArrayList<>();
        List<String> restaurantList = new ArrayList<>();

        eventList.add("Event 1");
        eventList.add("Event 2");
        realEstateList.add("RE 1");
        realEstateList.add("RE 2");
        restaurantList.add("RG 1");
        restaurantList.add("RG 2");

        newCity.setEvents(eventList);
        newCity.setRealEstate(realEstateList);
        newCity.setRestaurants(restaurantList);

        model.addAttribute("newCity", newCity);

        return "hello";
    }
}
