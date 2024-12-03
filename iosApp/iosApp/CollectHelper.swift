//
//  CollectHelper.swift
//  iosApp
//
//  Created by Bishal EUR0409 on 15/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

func collect<T>( flow:CommonStateFlow<T> , onEach : @escaping (T)-> Void) async {
    
    var countinuation : CheckedContinuation<Void,Never>?
    
    let cancellable  =  flow.startCollection(onEach: {value in onEach(value!)}) {
        countinuation?.resume()
    }
    
    await withTaskCancellationHandler(operation: {
        await withCheckedContinuation{value in countinuation = value}
    }, onCancel: {
        cancellable.onCancel()
    })
}
