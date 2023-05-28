package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.BuchRepository;
import de.olivergeisel.teddjbrary.core.ISBN;
import de.olivergeisel.teddjbrary.rooms.Werkstatt;
import de.olivergeisel.teddjbrary.structure.NoMatchingBookException;
import de.olivergeisel.teddjbrary.user.staff.VerwaltungsException;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import de.olivergeisel.teddjbrary.user.visitor.Kundenregister;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BestandsVerwaltung {

    private final RegalRepository regale;
    private final BuchRepository buchRepository;

    private final Kundenregister kundenregister;
    private final Werkstatt werkstatt;

    public BestandsVerwaltung(Kundenregister kundenregister, RegalRepository regale, BuchRepository buchRepository) {
        this.kundenregister = kundenregister;
        this.regale = regale;
        this.buchRepository = buchRepository;
        werkstatt = new Werkstatt(this);
    }


    /**
     * Sucht nach dem Regal, das das gesuchte Buch enthält.
     * <p>
     *
     * @param buch Buch zu dem das Regal gefunden werden soll.
     * @return Regal in dem das Buch ist.
     * @throws NoSuchElementException wenn das Buch in keinem Regal ist.
     */
    public RegalCode getRegalCode(Buch buch) throws NoSuchElementException {
        return regale.findFirstByInhalt_Buecher(buch).orElseThrow().getCode();
    }

    public Buch sucheNachISBN(ISBN isbn) {
        var alleBuecher = buchRepository.findAll().toList();
        int index = Collections.binarySearch(alleBuecher, new Buch("Testbuch", isbn));
        if (index < 0) {
            return null;
        }
        return alleBuecher.get(index);
    }

    public Collection<Buch> sucheNachAuthor(String author) {
        Set<Buch> back = new HashSet<>();
        for (Buch buch : buchRepository.findAll()) {
            if (author.equals(buch.getAutor())) {
                back.add(buch);
            }
        }
        return back;
    }

    public Buch sucheNachTitel(String titel) {
        for (Buch buch : buchRepository.findAll()) {
            if (buch.getTitel().equals(titel))
                return buch;
        }
        return null;
    }

    public Buch sucheNachTreffer(String text) {
        // todo implement freiwillig
        return null;
    }

    public void neuesBuchHinzufuegen(Buch buch, Regal regal) throws VerwaltungsException {
        // TODO check wie handhaben
        if (buchRepository.existsById(buch.getId())) {
            throw new VerwaltungsException("Das Buch ist bereits in der Bibliothek");
        }
        buchRepository.save(buch);
        regal.hineinStellen(buch);
        regale.save(regal);
    }

    public void neuesBuchHinzufuegen(Buch buch) {
        Regal regal = getNichtVollesRegal();
        neuesBuchHinzufuegen(buch, regal);
    }

    public boolean zurueckgeben(Buch buch) {
        boolean back = kundenregister.gibBuchZurueck(buch);
        buch.verfuegbarMachen();
        if (buch.getBeschaedigung() >= 0.8)
            werkstatt.zurReparaturHinzufuegen(buch);
        inEinRegalStellen(buch);
        return back;
    }

    /**
     * Stellt ein bereits registriertes Buch in ein Regal.
     * <p>
     * Diese Methode soll genutzt werden, wenn das Buch bereits in der Bibliothek registriert wurde
     *
     * @param buch Buch, das hineingestellt werden soll
     * @return Regal, in das das Buch hineingestellt wurde.
     * @throws VerwaltungsException,  wenn kein Regal mehr Platz hat.
     * @throws IllegalStateException, wenn das Buch noch nicht registriert ist.
     */
    public Regal inEinRegalStellen(Buch buch) throws VerwaltungsException, IllegalStateException {
        Regal verweis = getNichtVollesRegal();
        if (verweis == null) {
            throw new VerwaltungsException("Wir haben leider keinen Platz mehr!");
        }
        verweis.hineinStellen(buch);
        return regale.save(verweis);
    }


    public boolean ausleihen(Buch buch, Besucher besucher) {
        if (buch.isVerfuegbar()) {
            buch.ausleihen();
            kundenregister.leiheBuchAus(buch, besucher);
            ausRegalNehmen(buch);
        }
        return true;
    }

    /**
     * Nimmt ein Buch aus den Regalen.
     *
     * @param regal Regal, aus dem das Buch genommen werden soll.
     * @param buch  Buch, das aus dem Regal genommen werden soll.
     * @return true, wenn das Buch herausgenommen wurde; sonst false.
     * @throws NoMatchingBookException, wenn das Buch nicht im Regal ist.
     */
    boolean ausRegalNehmen(Buch buch, Regal regal) throws NoMatchingBookException {
        regal.herausnehmen(buch);
        regale.save(regal);
        return true;
    }

    /**
     * Nimmt ein Buch aus einem Regal, in dem das Buch steht.
     *
     * @param buch
     * @return Regal, das das Buch hatte.
     */
    public Regal ausRegalNehmen(Buch buch) throws NoSuchElementException {
        Regal back = null;
        for (Regal regal : regale.findAll()) {
            try {
                regal.herausnehmen(buch);
                back = regal;
                break;
            } catch (NoMatchingBookException ignored) {
            }
        }
        if (back == null) {
            throw new NoSuchElementException("Es gab leider kein Regal, das das Buch enthält.");
        }
        return back;
    }

    //
    public long getAnzahlBuecher() {
        return buchRepository.count();
    }

    public long getAnzahlBuecherVerschieden() {
        return buchRepository.countDistinctBooks();
    }

    public int getAnzahlVerschiedenerBuecher() {
        return buchRepository.findAll().stream().collect(Collectors.groupingBy(Buch::getIsbn)).size();
    }

    private Regal getNichtVollesRegal() {
        for (Regal regal : regale.findAll()) {
            if (!regal.isVoll()) {
                return regal;
            }
        }
        return null;
    }

    public Set<Regal> getRegale() {
        return regale.findAll().toSet();
    }

    public Werkstatt getWerkstatt() {
        return werkstatt;
    }
//

}

