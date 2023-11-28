package com.example.pokemonapp.detailPokemon.data.model

data class VersionsModel(val generationI: GenerationIModel,
                         val generationIi: GenerationIiModel,
                         val generationIii: GenerationIiiModel,
                         val generationIv: GenerationIvModel,
                         val generationV: GenerationVModel,
                         val generationVi: Map<String, HomeModel>,
                         val generationVii: GenerationViiModel,
                         val generationViii: GenerationViiiModel)
