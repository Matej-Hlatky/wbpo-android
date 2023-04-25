# WBPO Sample Android App

## Prerequisites

- Android SDK 33
- Java 11

## Used dependencies

- Standard XML view based system
- Kotlin, Coroutines
- MVVM, `LiveData`, `Flow`, Data Binding
- Material Design 3 for UI
- Dagger for Dependency Injection
- Retrofit as a REST client
- Coil for image loading
- Paging 3 for optimized loading of lists from paginated data sources

## App content

App contains two screens:

- **User Login / Registration (`UserLoginFragment`)**
  - can switch between login or registration
  - by taping on logo, form is prefilled with working credentials
- **User List (`UserListFragment`)**
  - displays paginated list of users with placehlders
  - handling network error withy retry functionality and auto retry when network is available again
  - grid will have two columns in larger screen (e.g. landscape on phone or portrait on tablet)
  - storing User following status persistently

## Things to do

1. **Tweak login screen**
   - displaying errors near inputs
   - use Coordinator Layout for screen
   - better UX for loading indication
2. **Tweak list screen**
   - extract some of the code into helpers
   - don't mutatate `User.isFollowed` but update it in data source with potential error handling
   - option to refresh list
3. **New User Details screen** - master-detail on tablet or new screen with transition on phone
4. **Use "Single live events"** but must handle loading error / dialogs on orientation change.
