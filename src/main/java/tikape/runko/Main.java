package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AinesosaDao;
import tikape.runko.database.Database;
import tikape.runko.database.ReseptiAinesosaDao;
import tikape.runko.database.ReseptiDao;
import tikape.runko.domain.Ainesosa;
import tikape.runko.domain.Resepti;
import tikape.runko.domain.ReseptiAinesosa;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/reseptit.db");
        database.init();

        ReseptiDao reseptiDao = new ReseptiDao(database);
        AinesosaDao ainesosaDao = new AinesosaDao(database);
        ReseptiAinesosaDao reseptiAinesosaDao = new ReseptiAinesosaDao(database);

        // Pääsivu
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("reseptit", reseptiDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        // Reseptin sivu
        get("/reseptit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("resepti", reseptiDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("ainesosat", ainesosaDao.findAll());
            map.put("reseptiAinesosa", reseptiAinesosaDao.findByResepti(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "resepti");
        }, new ThymeleafTemplateEngine());

        // Reseptin muokkaus
        get("/reseptit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("reseptit", reseptiDao.findAll());
            map.put("ainesosat", ainesosaDao.findAll());

            return new ModelAndView(map, "reseptit");
        }, new ThymeleafTemplateEngine());

        // Ainesosa  listaus ja lisäys
        get("/ainesosat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainesosat", ainesosaDao.findAll());

            return new ModelAndView(map, "ainesosat");
        }, new ThymeleafTemplateEngine());

        // Ainesosan lisääminen
        post("/ainesosat", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Ainesosa ainesosa = new Ainesosa(-1, nimi);
            ainesosaDao.SaveOrUpdate(ainesosa);

            res.redirect("/ainesosat");
            return "";
        });

        // Ainesosan poistaminen
        post("/ainesosat/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params("id"));
            ainesosaDao.delete(id);
            
            res.redirect("/ainesosat");
            return "";
        });

        // Uusi resepti nimi
        post("/resepti", (req, res) -> {
            String nimi = req.queryParams("resepti");
            Resepti resepti = new Resepti(-1, nimi, null);
            reseptiDao.saveOrUpdate(resepti);

            res.redirect("/reseptit");
            return "";
        });

        // Lisää ainesosa reseptiin
        post("/reseptit", (req, res) -> {
            Integer resepti = Integer.parseInt(req.queryParams("reseptit"));
            Integer ainesosa = Integer.parseInt(req.queryParams("ainesosa"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");

            reseptiAinesosaDao.saveOrUpdate(new ReseptiAinesosa(ainesosa, resepti, jarjestys, maara, ohje));

            res.redirect("/reseptit");
            return "";
        });
        
        // Reseptin poistaminen
        post("/resepti/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params("id"));
            reseptiDao.delete(id);
            
            res.redirect("/");
            return "";
        });
        
        // Valmistusohjeen lisääminen reseptiin
        get("/valmistusohje", (req, res) -> {
            Integer id = Integer.parseInt(req.queryParams("reseptit"));
            String ohje = req.queryParams("resepti");

            Resepti resepti = new Resepti(id, reseptiDao.findOne(id).getNimi(), ohje);
            reseptiDao.saveOrUpdate(resepti);

            res.redirect("/reseptit");
            return "";
        });

    }
}
