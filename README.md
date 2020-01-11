GithubRepo APP
This App follow MVVM architecture along with repository as an abstraction layer which interacts with Database and Network to fetch the data, This app also follow ​single source of truth ​as required, it fetches only from db, Network calls are made in the following scenarios
1. If app is opened for the first time and there is no data in db
2. If user uses pull-to-refresh for force fetch
3. If the data is older than 2 hours
TechStack​ used in this app
1. Retrofit and Okhttp with Coroutines​ : For Networking
2. Room with Livedata​: For offline support
3. Espresso​: For activity testing with all states
4. Mockito​: For Unit Testing of Repository
5. Dagger2 ​: For dependency injection


Every response from network and db are wrapped in a single data type ​RESOURCE, ​with status as a field which defines the state in which the call is.
There are three States ​LOADING, ERROR, SUCCESS


In loading state​, shimmer animation is loaded and other views are hidden
In error state​, given error screen is loaded and other views are hidden
In success state​, list of trending repos along with option to sort the list is loaded and loading and error screens are hidden.


Every response object from network call is added with current timestamp, so that next time when it is fetched from room db, we can check if it is older than 2 hours and if we need to fetch from remote again.
