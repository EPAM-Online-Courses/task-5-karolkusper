package efs.task.collections.entity;

import java.util.Objects;

public class Hero {
    private String name;
    private String heroClass;

    public Hero(String name, String heroClass) {
        this.name = name;
        this.heroClass = heroClass;
    }

    public String getName() {
        return name;
    }

    public String getHeroClass() {
        return heroClass;
    }

    //TODO implementacja metody equal porównująca obiekty Hero na podstawie pól name i heroClass.
    @Override
    public boolean equals(Object o) {

        if(this==o)
        {
            return true;
        }
        if(o==null || o.getClass()!=this.getClass())
        {
            return false;
        }

        Hero hero = (Hero) o;
        return (this.getName().equals(hero.getName())&&this.getHeroClass().equals(hero.getHeroClass()));


    }

    //TODO implementacja metody equal biorąca pod uwagę name i heroClass.
    @Override
    public int hashCode() {
        return Objects.hash(name, heroClass);
    }

    @Override
    public String toString() {
        return "My name is " + name + "and I am " + heroClass;
    }
}
