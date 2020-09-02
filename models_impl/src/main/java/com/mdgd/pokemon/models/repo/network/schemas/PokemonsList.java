package com.mdgd.pokemon.models.repo.network.schemas;

import java.util.List;

public class PokemonsList {
    private int count;
    private String next;
    private String previous;
    private List<PokemonData> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PokemonData> getResults() {
        return results;
    }

    public void setResults(List<PokemonData> results) {
        this.results = results;
    }
}
