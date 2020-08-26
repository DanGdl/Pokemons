package com.mdgd.pokemon.ui.pokemons.dto;

public class FilterData {
    private final boolean byAttack;
    private final boolean byDefence;
    private final boolean byMovement;

    public FilterData(boolean byAttack, boolean byDefence, boolean byMovement) {
        this.byAttack = byAttack;
        this.byDefence = byDefence;
        this.byMovement = byMovement;
    }
}
