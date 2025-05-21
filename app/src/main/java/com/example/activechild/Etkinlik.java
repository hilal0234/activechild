package com.example.activechild;
public class Etkinlik {
    private String baslik;
    private String aciklama;
    private String resimUrl;
    private String kaynakUrl;

    public Etkinlik() {}

    public Etkinlik(String baslik, String aciklama, String resimUrl, String kaynakUrl) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.resimUrl = resimUrl;
        this.kaynakUrl = kaynakUrl;
    }

    public String getBaslik() { return baslik; }
    public String getAciklama() { return aciklama; }
    public String getResimUrl() { return resimUrl; }
    public String getKaynakUrl() { return kaynakUrl; } // ← BU ŞART
}
