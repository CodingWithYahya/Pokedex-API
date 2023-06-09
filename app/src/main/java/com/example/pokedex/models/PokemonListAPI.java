/*
 *
 *   Created Yahya Quazbari on 11/04/2023 22:41
 *   Copyright Ⓒ 2023. All rights reserved Ⓒ 2023 http://freefuninfo.com/
 *   Last modified: 11/04/2023 18:29
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.example.pokedex.models;

import java.util.ArrayList;

public class PokemonListAPI {

    private ArrayList<Pokedex> results;

    public ArrayList<Pokedex> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokedex> results) {
        this.results = results;
    }


}
