/*
 *
 *   Created Yahya Quazbari on 11/04/2023 22:41
 *   Copyright Ⓒ 2023. All rights reserved Ⓒ 2023 http://freefuninfo.com/
 *   Last modified: 11/04/2023 22:13
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pokedex.adapter.PokemonAdapter;
import com.example.pokedex.models.Pokedex;
import com.example.pokedex.models.PokemonListAPI;
import com.example.pokedex.service.PokemonService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG ="YahyaPokedex";

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    private boolean fitForLoad;

    private int offset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.pokemonRecyclerView);
        pokemonAdapter = new PokemonAdapter(this, new PokemonAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Pokedex pokemon) {
                Intent intent = new Intent(getApplicationContext(),PokemonDetailActivity.class);
                intent.putExtra("name",pokemon.getName());
                intent.putExtra("id", pokemon.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){
                    int visibleitemcount = gridLayoutManager.getChildCount();
                    int totalitemcount= gridLayoutManager.getItemCount();
                    int pastvisibleitems=gridLayoutManager.findFirstVisibleItemPosition();

                    if(fitForLoad){
                        if((visibleitemcount+pastvisibleitems)>=totalitemcount){
                            Log.i(TAG,"Reached Final Item");

                            fitForLoad=false;
                            offset+=20;
                            getData(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fitForLoad=true;
        offset = 0;
        getData(offset);
    }

    private void getData(int offset){
        PokemonService pokemonService = retrofit.create(PokemonService.class);
        Call<PokemonListAPI> pokemonAPIAnswerCall = pokemonService.getListPokemon(20, this.offset);

        pokemonAPIAnswerCall.enqueue(new Callback<PokemonListAPI>() {
            @Override
            public void onResponse(Call<PokemonListAPI> call, Response<PokemonListAPI> response) {
                fitForLoad=true;
                if(response.isSuccessful()){
                    PokemonListAPI pokemonAPIAnswer = response.body();
                    ArrayList<Pokedex> pokemons = pokemonAPIAnswer.getResults();
                    pokemonAdapter.addPokemonListe(pokemons);

                }else{
                    Log.e(TAG," Response : "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonListAPI> call, Throwable t) {
                fitForLoad=true;
                Log.e(TAG," Failure : "+t.getMessage());
            }
        });
    }
}