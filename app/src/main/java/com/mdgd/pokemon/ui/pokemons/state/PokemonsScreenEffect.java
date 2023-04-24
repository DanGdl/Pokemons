package com.mdgd.pokemon.ui.pokemons.state;

import com.mdgd.mvi.states.AbstractEffect;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;

public class PokemonsScreenEffect extends AbstractEffect<PokemonsContract.View> {

    public static class ShowDetails extends PokemonsScreenEffect {

        private final Long id;

        public ShowDetails(Long id) {
            this.id = id;
        }

        @Override
        protected void handle(PokemonsContract.View screen) {
            screen.proceedToNextScreen(id);
        }
    }


    public static class ShowErrorEffect extends PokemonsScreenEffect {

        private final Throwable error;

        public ShowErrorEffect(Throwable e) {
            error = e;
        }

        @Override
        protected void handle(PokemonsContract.View screen) {
            screen.showError(error);
        }
    }

}
