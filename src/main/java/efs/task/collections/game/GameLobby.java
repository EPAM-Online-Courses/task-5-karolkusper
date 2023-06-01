package efs.task.collections.game;

import efs.task.collections.data.DataProvider;
import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.util.*;
import java.util.stream.Collectors;

public class GameLobby {

    public static final String HERO_NOT_FOUND = "Nie ma takiego bohatera ";
    public static final String NO_SUCH_TOWN = "Nie ma takiego miasta ";

    private final DataProvider dataProvider;
    private Map<Town, List<Hero>> playableTownsWithHeroesList;

    public GameLobby() {
        this.dataProvider = new DataProvider();
        this.playableTownsWithHeroesList =
                mapHeroesToStartingTowns(dataProvider.getTownsList(), dataProvider.getHeroesSet());
    }

    public Map<Town, List<Hero>> getPlayableTownsWithHeroesList() {
        return playableTownsWithHeroesList;
    }

    //TODO Dodać miasta i odpowiadających im bohaterów z DLC gry do mapy dostępnych
    // miast - playableTownsWithHeroesList, tylko jeżeli jeszcze się na niej nie znajdują.
    public void enableDLC() {
        List<Town> dlcTowns = this.dataProvider.getDLCTownsList();
        Set<Hero> dlcHeroes = this.dataProvider.getDLCHeroesSet();
        for (Town town : dlcTowns) {
            if (!playableTownsWithHeroesList.containsKey(town)) {
                List<Hero> heroesAtTown = dlcHeroes.stream()
                        .filter(hero -> town.getStartingHeroClasses().contains(hero.getHeroClass()))
                        .collect(Collectors.toList());
                playableTownsWithHeroesList.put(town, heroesAtTown);
            }
        }
    }


    //TODO Usunąć miasta i odpowiadających im bohaterów z DLC gry z mapy dostępnych
    // miast - playableTownsWithHeroesList.
    public void disableDLC() {
        for (Town town : this.dataProvider.getDLCTownsList()) {
            this.playableTownsWithHeroesList.remove(town);
        }
    }

    // TODO Sprawdza czy mapa playableCharactersByTown zawiera dane miasto.
    //  Jeśli tak zwróć listę bohaterów z tego miasta.
    //  Jeśli nie rzuć wyjątek NoSuchElementException z wiadomością NO_SUCH_TOWN + town.getName()
    public List<Hero> getHeroesFromTown(Town town) {
        if (this.playableTownsWithHeroesList.containsKey(town)) {
            return this.playableTownsWithHeroesList.get(town);
        } else {
            throw new NoSuchElementException(NO_SUCH_TOWN + town.getTownName());
        }
    }

    // TODO Metoda powinna zwracać mapę miast w kolejności alfabetycznej z odpowiadającymi im bohaterami.
    //  Każde z miast charakteryzuje się dwoma klasami bohaterów dostępnymi dla tego miasta - Town.startingHeroClass.
    //  Mapa ma zawierać pare klucz-wartość gdzie klucz: miasto, wartość: lista bohaterów;
    public Map<Town, List<Hero>> mapHeroesToStartingTowns(List<Town> availableTowns, Set<Hero> availableHeroes) {
        Map<Town, List<Hero>> mapTownHero = new TreeMap<>((town1, town2) -> town1.getTownName().compareTo(town2.getTownName()));
        for (Town town : availableTowns) {
            List<Hero> heroAtTown = availableHeroes.stream()
                    .filter(hero -> town.getStartingHeroClasses().contains(hero.getHeroClass()))
                    .collect(Collectors.toList());
            mapTownHero.put(town, heroAtTown);
        }
        return mapTownHero;
    }

    //TODO metoda zwraca wybranego bohatera na podstawie miasta z którego pochodzi i imienia.
    // Jeżeli istnieje usuwa go z listy dostępnych bohaterów w danym mieście i zwraca bohatera.
    // Jeżeli nie ma go na liście dostępnych bohaterów rzuca NoSuchElementException z wiadomością HERO_NOT_FOUND + name
    public Hero selectHeroByName(Town heroTown, String name) {
        List<String> namesFromTown = this.playableTownsWithHeroesList.get(heroTown).stream().map(Hero::getName).collect(Collectors.toList());


        if (namesFromTown.contains(name)) {
            this.playableTownsWithHeroesList.get(heroTown).removeIf(hero -> hero.getName().equals(name));
            for (Hero hero : this.dataProvider.getHeroesSet()) {
                if (name.equals(hero.getName())) {
                    return hero;
                }
            }
        } else {
            throw new NoSuchElementException(HERO_NOT_FOUND + name);
        }
        return null;
    }

}