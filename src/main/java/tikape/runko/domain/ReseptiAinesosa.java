package tikape.runko.domain;

public class ReseptiAinesosa {
    
    private Integer ainesosaId;
    private Integer reseptiId;
    private Integer jarjesys;
    private String maara;
    private String ohje;
    
    public ReseptiAinesosa (Integer ainesosaId, Integer reseptiId, Integer jarjestys, String maara, String ohje) {
        this.ainesosaId = ainesosaId;
        this.reseptiId = reseptiId;
        this.jarjesys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public Integer getAinesosaId() {
        return ainesosaId;
    }
    
    public Integer getReseptiId() {
        return reseptiId;
    }
    
    public Integer getJarjestys() {
        return jarjesys;
    }
    
    public String getMaara() {
        return maara;
    }
    
    public String getOhje() {
        return ohje;
    }
    
}
