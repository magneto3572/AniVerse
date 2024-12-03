import SwiftUI

struct HomeView: View {
    
    @ObservedObject var homeViewModelWrapper = HomeViewModelWrapper()
    @State private var currentIndex = 0 // Track the current carousel index
    
    var body: some View {
        NavigationStack {
            ZStack {
                Color.black.edgesIgnoringSafeArea(.all) // Background color
                
                if homeViewModelWrapper.uiState.isLoading {
                    ProgressView()
                        .scaleEffect(1.5)
                        .progressViewStyle(CircularProgressViewStyle(tint: .orange))
                } else if let animeHome = homeViewModelWrapper.uiState.animeHome,
                          !animeHome.recentReleases.isEmpty {
                    ScrollView {
                        VStack(spacing: 20) {
                            
                            // Top Banner Section with carousel and gradient overlay
                            ZStack {
                                let animeItems = animeHome.recentReleases.prefix(5).map { AnimeItem(id: $0.id, title: $0.name, img: $0.img) }
                                CarouselView(releases: animeItems, imageHeight: 400, currentIndex: $currentIndex)
                                
                                // Add to Wishlist Button above the gradient and images
                                VStack {
                                    Spacer()
                                    Text(animeItems[currentIndex].title)
                                        .font(.headline)
                                        .foregroundColor(.white)
                                        .lineLimit(2) // Allow up to 2 lines
                                        .truncationMode(.tail) // Truncate the text with "..." if it overflows
                                        .frame(maxWidth: .infinity) // Make the text frame take full width
                                        .multilineTextAlignment(.center) // Align text to the center horizontally
                                        .padding(.horizontal, 10)
                                    
                                    AddToWishlistButton()
                                        .padding(.bottom, 45)
                                }
                                .padding(.top, 50)
                            }
                            .frame(height: 400)
                            
                            // Ongoing Series Section
                            if !animeHome.onGoingSeries.isEmpty {
                                let animeItems = animeHome.onGoingSeries.map { AnimeItem(id: $0.id, title: $0.name, img: $0.img) }
                                HorizontalAnimeListView(title: "Ongoing Series", width: 150, animeList: animeItems)
                            }
                            
                            // Popular Anime Section
                            if !(homeViewModelWrapper.uiState.popularAnimeItemList?.isEmpty ?? true) {
                                let animeItems = homeViewModelWrapper.uiState.popularAnimeItemList?.map { AnimeItem(id: $0.id, title: $0.name, img: $0.img) } ?? []
                                HorizontalAnimeListView(title: "Popular Anime", width: 270, animeList: animeItems)
                            }
                            
                            // Recently Added Section
                            if !animeHome.recentlyAddedSeries.isEmpty {
                                let animeItems = animeHome.recentlyAddedSeries.map { AnimeItem(id: $0.id, title: $0.name, img: $0.img) }
                                HorizontalAnimeListView(title: "Recently Added", width: 150, animeList: animeItems)
                            }
                            
                            // Anime Movies Section
                            if !(homeViewModelWrapper.uiState.animeMovieItemList?.isEmpty ?? true) {
                                let animeItems = homeViewModelWrapper.uiState.animeMovieItemList?.map { AnimeItem(id: $0.id, title: $0.name, img: $0.img) } ?? []
                                HorizontalAnimeListView(title: "Anime Movies", width: 150, animeList: animeItems)
                            }
                        }
                        .padding(.bottom, 20)
                    }
                } else {
                    // Fallback UI when no data is available
                    Text("No data available")
                        .foregroundColor(.gray)
                        .font(.headline)
                }
            }
            .edgesIgnoringSafeArea(.top)
            .preferredColorScheme(.dark)
        }
    }
}

struct CarouselView: View {
    let releases: [AnimeItem]
    let imageHeight: CGFloat
    @Binding var currentIndex: Int
    @State private var timer: Timer? = nil
    
    var body: some View {
        ZStack {
            GeometryReader { geometry in
                // Image Carousel
                HStack(spacing: 0) {
                    ForEach(releases, id: \.self) { release in
                        AsyncImage(url: URL(string: release.img)) { image in
                            image
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                                .frame(width: geometry.size.width, height: imageHeight)
                                .clipped()
                        } placeholder: {
                            Rectangle()
                                .fill(Color.gray.opacity(0.5))
                                .frame(width: geometry.size.width, height: imageHeight)
                        }
                    }
                }
                .offset(x: -CGFloat(currentIndex) * geometry.size.width)
                .animation(.easeInOut, value: currentIndex)
                .onAppear {
                    startAutoScroll()
                }
                .onDisappear {
                    stopAutoScroll()
                }
            }
            .frame(height: imageHeight)
            
            // Gradient Overlay above the images
            LinearGradient(
                gradient: Gradient(colors: [Color.black.opacity(1.0), Color.black.opacity(0.6), Color.clear]),
                startPoint: .bottom,
                endPoint: .top
            )
            .frame(height: imageHeight)
            
            // Dot Indicator above the gradient
            VStack {
                Spacer()  // This pushes the dot indicator to the bottom
                
                HStack {
                    ForEach(0..<releases.count, id: \.self) { index in
                        Circle()
                            .fill(index == currentIndex ? Color.white : Color.gray.opacity(0.5))
                            .frame(width: 8, height: 8)
                    }
                }
                .padding(.bottom, 20)  // Adjust the padding if needed
            }
            .frame(maxHeight: .infinity, alignment: .bottom)  // Ensure the VStack takes up full height and aligns the content to the bottom
            
        }
        .frame(height: imageHeight)
    }
    
    private func startAutoScroll() {
        timer = Timer.scheduledTimer(withTimeInterval: 3.0, repeats: true) { _ in
            withAnimation {
                if currentIndex < releases.count - 1 {
                    currentIndex += 1
                } else {
                    currentIndex = 0 // Loop back to the first image
                }
            }
        }
    }
    
    private func stopAutoScroll() {
        timer?.invalidate()
        timer = nil
    }
}

struct HorizontalAnimeListView: View {
    let title: String
    let width: CGFloat
    let animeList: [AnimeItem]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(title)
                .font(.subheadline)
                .foregroundColor(.white)
                .padding(.horizontal)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 10) {
                    ForEach(animeList, id: \.self) { anime in
                        AsyncImage(url: URL(string: anime.img)) { image in
                            ZStack {
                                image
                                    .resizable()
                                    .aspectRatio(contentMode: .fill)
                                    .frame(width: width, height: 160)
                                    .cornerRadius(14)
                                
                                // Gradient overlay
                                LinearGradient(
                                    gradient: Gradient(colors: [Color.black.opacity(1.0),Color.black.opacity(0.5), Color.clear]),
                                    startPoint: .bottom,
                                    endPoint: .top
                                )
                                .cornerRadius(14)  // Match the image corner radius
                                
                                // Text overlay (Anime name) above the gradient
                                VStack {
                                    Spacer()
                                    HStack {
                                        Text(anime.title)
                                            .font(.caption) // Use smaller text size
                                            .foregroundColor(.white.opacity(0.8))
                                            .lineLimit(1) // Limit text to one line
                                            .truncationMode(.tail) // Truncate with "..." if it's too long
                                            .padding(.bottom, 10)
                                            .padding(.horizontal, 10)
                                            .shadow(radius: 10) // Optional, for better visibility on dark images
                                    }
                                    .frame(maxWidth: .infinity, alignment: .leading) // Align text to the left
                                }
                                .padding(.top, 10)
                            }
                        } placeholder: {
                            Rectangle()
                                .fill(Color.gray.opacity(0.5))
                                .frame(width: width, height: 160)
                                .cornerRadius(14)
                        }
                        .frame(width: width, height: 160)
                    }
                }
                .padding(.horizontal)
            }
        }
    }
}

struct AddToWishlistButton: View {
    var body: some View {
        Button(action: {
            print("Add to Wishlist clicked")
        }) {
            HStack {
                Image(systemName: "plus")
                    .foregroundColor(.white)
                
                Text("Add to Wishlist")
                    .font(.body)
                    .foregroundColor(.white)
            }
            .padding(.horizontal, 32)
            .padding(.vertical, 16)
            .background(Color.black.opacity(0.8))
            .clipShape(Capsule())
        }
    }
}

struct AnimeItem: Hashable {
    let id: String
    let title: String
    let img: String
}

// Add Preview provider to show the HomeView
struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
