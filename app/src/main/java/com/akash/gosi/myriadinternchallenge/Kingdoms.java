package com.akash.gosi.myriadinternchallenge;


import java.util.List;

public class Kingdoms {
    private String id;
    private String name;
    private String image;
    private String climate;
    private Integer population;
    private String language;
    private List<Quests> quests;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public List<Quests> getQuests() {
        return quests;
    }

    public void setQuests(List<Quests> quests) {
        this.quests = quests;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class Giver{

        public String id;
        public String name;
        public String image;
    }


    public static class Quests{
        public String id;
        public String name;
        public String description;
        public String image;
        public Giver giver;


        public Quests(){

            giver = new Giver();
        }

        @Override
        public boolean equals(Object o) {
            boolean same = false;

                if (o != null && o instanceof Quests)
                {
                    Quests copyO = (Quests) o;

                    if(this.id.equals(copyO.id) && this.name.equals(copyO.name))
                        same=true;

                }
            return same;
        }
    }

}

