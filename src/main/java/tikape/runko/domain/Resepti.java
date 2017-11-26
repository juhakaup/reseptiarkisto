package tikape.runko.domain;

public class Resepti {
    
    private Integer id;
    private String nimi;
    private String ohje;
    
    public Resepti(Integer id, String nimi, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.ohje = ohje;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }
    
    public String getOhje() {
        return ohje;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
}
