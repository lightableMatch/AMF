package com.example.ApexMapFinder.controller;

import com.example.ApexMapFinder.dto.*;
import com.example.ApexMapFinder.other.DynamicSchedulingConfig;
import com.example.ApexMapFinder.service.AMFService;
import com.example.ApexMapFinder.service.NotificationService;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    @Autowired
    private AMFService amfService;
    @Autowired
    private NotificationService notificationSerivce;
    Notification notification = new Notification();

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);


    //Method to get current api making an api call n such
    @GetMapping("/amf")
    @ResponseBody
    public AMF amf(){
        AMF amf = amfService.getAMF();
        return amf;
    }

    //test for returning an html response
    @GetMapping("/")
    public String index(Model model) {
//        AMF amfPOJO = amfService.getAMF();
//        String currentBRImg = amfService.getMapImage("current", "battleRoyale");
//        String nextBRImg = amfService.getMapImage("next", "battleRoyale");
//        String currentArenaImg = amfPOJO.getArenas().getCurrent().getMap();
//        String nextArenaImg = amfPOJO.getArenas().getNext().getMap();
//
//        model.addAttribute(amfPOJO);
//        model.addAttribute("current", currentBRImg);
//        model.addAttribute("next", nextBRImg);
//        model.addAttribute("currentArena", currentArenaImg);
//        model.addAttribute("nextArena", nextArenaImg);

        return "homePage";
    }

    @GetMapping("/map_prototype")
    public String mapPrototype(Model model){
        AMF amf = amfService.getAMF();

        model.addAttribute("amf", amf);
        model.addAttribute("currentBR", amfService.getMapImage("current", "battleRoyale"));
        model.addAttribute("currentArena", amfService.getMapImage("current", "arenas"));
        model.addAttribute("currentRanked", amfService.getMapImage("current", "ranked"));
        model.addAttribute("currentArenaRanked", amfService.getMapImage("current", "arenaRanked"));
        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));

        return "mapPrototype";
    }
    @GetMapping("/current")
    public String currentMaps(Model model) {
        AMF amf = amfService.getAMF();
        if(amf == null){
            log.info("null amf");
            amf = amfService.updateAMF();
        }
        model.addAttribute("amf", amf);

        model.addAttribute("currentBR", amfService.getMapImage("current", "battleRoyale"));
        model.addAttribute("currentArena", amfService.getMapImage("current", "arenas"));
        model.addAttribute("currentRanked", amfService.getMapImage("current", "ranked"));
        model.addAttribute("currentArenaRanked", amfService.getMapImage("current", "arenaRanked"));

        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));
        model.addAttribute("battleRoyaleRankedTimer", amfService.getEndTimer().get("battleRoyaleRanked"));

        return "currentMaps";
    }

    @GetMapping("/next")
    public String nextMaps(Model model) {
        AMF amf = amfService.getAMF();
        if(amf == null){
            log.info("null amf");
            amf = amfService.updateAMF();
        }
        model.addAttribute("amf", amf);

        model.addAttribute("nextBR", amfService.getMapImage("next", "battleRoyale"));
        model.addAttribute("nextArena", amfService.getMapImage("next", "arenas"));
        model.addAttribute("nextRanked", amfService.getMapImage("next", "ranked"));
        model.addAttribute("nextArenaRanked", amfService.getMapImage("next", "arenaRanked"));

        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));
        model.addAttribute("battleRoyaleRankedTimer", amfService.getEndTimer().get("battleRoyaleRanked"));

        return "nextMaps";
    }
    //mapping for returning json with certain player data
    @GetMapping("/player")
//    @ResponseBody
    public String getPlayer(@RequestParam String name, Model model) {
        Player player = amfService.getPlayerByName(name);
        if(player.getGlobal() == null)
        {
            return "playerNotFound";
        }
        model.addAttribute("player", player);
            return "player";
    }

    @GetMapping("/players")
    @ResponseBody
    public List<Player> getAllPlayers() {
        return amfService.getAllPlayers();
    }

    @GetMapping("/notificationSignUp")
    public String notificationSignUp(Model model){
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
        return "notificationSignUp";
    }

    @PostMapping("/deleteNotification")
    public String deleteNotificationByEmail(@RequestParam String email, Model model){

        boolean emailExists = notificationSerivce.emailExists(email);
        log.info("Deleting notifcations for: " + email + " | " + emailExists);
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
        try{
            notificationSerivce.deleteNotification(email);
        }
        catch (Exception e){
            model.addAttribute("emailNotExists", !emailExists);
            return "notificationSignUp";
        }

        model.addAttribute("deleteSuccess", true);
        return "notificationSignUp";
    }

    @GetMapping("/notificationDeletePage")
    public String notificationDeletePage(){
        return "notificationDelete";
    }

    @PostMapping("/saveNotification")
    public String saveNotification(@Valid @ModelAttribute("notification") Notification notification, BindingResult bindingResult, Model model){
        boolean emailExists = notificationSerivce.emailExists(notification.getEmail());
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));

        if (bindingResult.hasErrors() || emailExists){
            model.addAttribute("emailExists", emailExists);
            return "notificationSignUp";
        }
        System.out.println(notification.toString());

        notificationSerivce.saveNotification(notification);

        System.out.println(notificationSerivce.getNotification(notification.getEmail()));
        model.addAttribute("signupSuccess", true);
        return "notificationSignUp";
    }

}
