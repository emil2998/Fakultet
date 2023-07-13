package hr.foi.air.core

interface Komunikator {
    fun ulogirajKorisnika(korisnik: Korisnik)
    fun otvoriUpravljanjeKorisnicima()
    fun otvoriGlavniMeni()
    fun odjaviSe()
    fun aktivniPoslovi()
    fun trenutniPrijevoz()
    fun otvoriOpisPosla(popravak: Popravak)
    fun otvoriUpravljanjeVozilima()
    fun podnesiKvar(listaPrijevoza : Prijevozi)
    fun vratiGlavniMeni()

    fun otvoriMapu()

    fun otvoriDogovorenePoslove()
    fun proslijediOdabranPosao(idDogovorenPosao: String, adrsUtovar: String, adrsIstovar: String, brojTura: String)
    fun otvoriInfo(position: Int, idVozaca: String)
    fun otvoriProfil()
    fun otvoriPovijest()
    fun otvoriPrijavaLozinka()

    fun otvoriVozilaPrikolice()
    fun otvoriPovijestKvarova(id: String)
}
