//
//  HomeViewModelWrapper.swift
//  iosApp
//
//  Created by Bishal EUR0409 on 15/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared


class HomeViewModelWrapper : ObservableObject {
    
    var viewmodel : AnimeViewModel

    @Published var uiState : AnimeUiState = AnimeUiState.init(isLoading: false, isError: false, animeHome: AnimeResponse(genres: [], onGoingSeries: [], recentReleases: [], recentlyAddedSeries: []), animeMovieItemList: [], popularAnimeItemList: [],  errorMessage: "")

    var task : Task<Void, Never>?

    init(){
        viewmodel = ProvideViewModel.shared.getAnimeViewModel()
        task = Task{@MainActor[weak self] in
            if(self != nil){
                await collect(flow : self!.viewmodel.uiState as! CommonStateFlow<AnimeUiState>, onEach : {value in self!.uiState = value})
            }
        }
    }

    deinit{
        task?.cancel()
    }
}

