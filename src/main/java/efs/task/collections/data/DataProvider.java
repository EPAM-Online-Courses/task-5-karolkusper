package efs.task.collections.data;

import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.util.*;
import java.util.stream.Collectors;


public class DataProvider {

    public static final String DATA_SEPARATOR = ",";

    // TODO Utwórz listę miast na podstawie tablicy Data.baseTownsArray.
    //  Tablica zawiera String zawierający nazwę miasta oraz dwie klasy bohatera związane z tym miastem oddzielone przecinkami.
    //  Korzystając z funkcji split() oraz stałej DATA_SEPARATOR utwórz listę obiektów klasy efs.task.collections.entities.Town.
    //  Funkcja zwraca listę obiektów typu Town ze wszystkimi dziewięcioma podstawowymi miastami.
    public List<Town> getTownsList() {
        List<Town> list = new ArrayList<>();
        String[] values;
        String townName;
        List<String> startingHeroClasses;

        for (String miasto : Data.baseTownsArray) {
            values = miasto.split(DATA_SEPARATOR);
            townName = values[0].trim();

            startingHeroClasses = new ArrayList<>();
            startingHeroClasses.add(values[1].trim());
            startingHeroClasses.add(values[2].trim());

            list.add(new Town(townName, startingHeroClasses));
        }

        return list;
    }


    //TODO Analogicznie do getTownsList utwórz listę miast na podstawie tablicy Data.DLCTownsArray
    public List<Town> getDLCTownsList() {
        return Arrays.stream(Data.dlcTownsArray)
                .map(miasto->miasto.split(DATA_SEPARATOR))
                .map(values->{
                    String townName = values[0].trim();
                    List<String> startingHeroClasses = Arrays.asList(values[1].trim(),values[2].trim());
                    return new Town(townName,startingHeroClasses);
                })
                .collect(Collectors.toList());
    }

    //TODO Na podstawie tablicy Data.baseCharactersArray utworzyć listę bohaterów dostępnych w grze.
    // Tablica Data.baseCharactersArray zawiera oddzielone przecinkiem imie bohatera, klasę bohatera.
    // Korzystając z funkcji split() oraz DATA_SEPARATOR utwórz listę unikalnych obiektów efs.task.collections.entities.Hero.
    // UWAGA w Data.baseCharactersArray niektórzy bohaterowie powtarzają się, do porównania bohaterów używamy zarówno imie jak i jego klasę;
    public Set<Hero> getHeroesSet() {
        Set<Hero> uniqueHeroes=new HashSet<>();
        for(String hero : Data.baseCharactersArray){
            String [] values = hero.split(DATA_SEPARATOR);
            String name=values[0].trim();
            String heroClass=values[1].trim();

            uniqueHeroes.add(new Hero(name,heroClass));

        }
        return  uniqueHeroes;
    }

    //TODO Analogicznie do getHeroesSet utwórz listę bohaterów na podstawie tablicy Data.DLCCharactersArray
    public Set<Hero> getDLCHeroesSet() {
        return Arrays.stream(Data.dlcCharactersArray)
                .map(character->character.split(DATA_SEPARATOR))
                .map(values->{
                    String name=values[0].trim();
                    String heroClass=values[1].trim();
                    return new Hero(name,heroClass);
                })
                .distinct()
                .collect(Collectors.toSet());
    }
}
