# GitHub Copilot Instructions for Alfie-KMP

## Project Overview

Alfie-KMP is a Kotlin Multiplatform application with shared business logic and UI across Android and iOS using Compose Multiplatform. Follow these guidelines when contributing code to ensure consistency and quality.

## Architecture & Design Patterns

### Clean Architecture
- Maintain strict separation between **Presentation** (UI), **Domain** (business logic), and **Data** (data access) layers
- UI layer should never directly access data sources - always go through the repository
- Domain models should be platform-agnostic with no framework dependencies
- Keep business logic isolated and testable

### MVVM Pattern
- Use **ViewModels** for all UI logic and state management
- ViewModels should expose immutable state via `StateFlow`
- Screens are composables that observe ViewModel state using `collectAsStateWithLifecycle()`
- Never store UI state in composables - always hoist to ViewModels
- Apply unidirectional data flow: Events go up, State flows down

### Repository Pattern
- All data access must go through repository interfaces
- Repositories coordinate between remote (API) and local (Storage) data sources
- Keep repositories as thin coordinators - business logic belongs in domain/use cases
- Use dependency injection for repository implementations

## Kotlin & KMP Best Practices

### Code Style
- Use **data classes** for models and DTOs
- Use **sealed classes** for representing state variants and navigation destinations
- Leverage **null safety** - avoid `!!` operator, prefer safe calls `?.` or `let` blocks
- Prefer **immutability** - use `val` over `var`, immutable collections
- Use **coroutines** for asynchronous operations
- Use **Flow** for reactive streams only when continuous data updates are expected
- Follow Kotlin naming conventions: camelCase for properties/functions, PascalCase for classes

### Type Safety
- Enable explicit API mode where applicable
- Use strongly-typed models instead of primitive types
- Leverage sealed interfaces for navigation with kotlinx.serialization
- Avoid `Any` types - use generics with proper bounds

### Error Handling
- Use `try-catch` blocks for expected exceptions
- Never swallow exceptions silently - log or propagate appropriately
- Handle `CancellationException` properly in coroutines (rethrow it)
- Provide meaningful error states in UI (loading, error, success, empty)

## Android-Specific Guidelines

### Compose UI
- **State hoisting**: Lift state to the appropriate level
- Use **remember** and **rememberSaveable** appropriately for state persistence
- Apply **Modifier** consistently and compose them properly
- Use **LazyColumn/LazyGrid** for lists, never iterate with `forEach`
- Leverage **derivedStateOf** for computed state to avoid unnecessary recompositions
- Use **stable** and **immutable** annotations for classes passed to composables

### Android Components
- Activities should be minimal - delegate to composables and ViewModels
- Use **Application** class only for global initialization (e.g., DI)
- Declare `android:exported` explicitly for all components with intent-filters
- Request only necessary permissions in AndroidManifest
- Use Material 3 theming consistently

### Lifecycle & Configuration
- Collect flows using `collectAsStateWithLifecycle()` not `collectAsState()`
- Handle configuration changes gracefully (screen rotation, dark mode)
- Use `viewModelScope` for ViewModel coroutines
- Cancel coroutines when no longer needed

## iOS-Specific Guidelines

### Swift Integration
- Keep Swift code in `iosApp` minimal - delegate to shared Kotlin code
- Use `@main` annotation for app entry point
- Properly bridge Kotlin Flow to Swift Combine/AsyncSequence if needed

### iOS Configuration
- Maintain bundle ID consistency with package naming
- Configure proper app name and display name
- Set up appropriate capabilities in Xcode project
- Handle platform-specific UI adaptations in `iosMain` source set

## Dependency Injection with Koin

### Module Organization
- Separate modules by layer: `dataModule`, `viewModelModule`, etc.
- Use `single` for singletons (repositories, APIs)
- Use `factory` for ViewModels and short-lived objects
- Initialize Koin in platform-specific application entry points

### Injection Best Practices
- Prefer **constructor injection** over field injection
- Keep module definitions close to the types they provide
- Use `get()` parameter in module definitions for dependencies
- Avoid circular dependencies

## Data Layer Standards

### Network & API
- Use **Ktor** for HTTP client across platforms
- Configure platform-specific engines (OkHttp for Android, Darwin for iOS)
- Implement proper timeout and retry logic
- Handle HTTP errors gracefully with appropriate status codes
- Use `kotlinx.serialization` for JSON parsing

### Data Models
- Create separate DTOs for network responses
- Map DTOs to domain models in repository layer
- Handle nullable fields from API safely
- Use `@Serializable` annotation for JSON classes

### Caching & Storage
- Implement caching strategy in repositories
- Use reactive streams (Flow) to notify observers of data changes
- Ensure thread-safety with proper coroutine dispatchers
- Keep storage implementations platform-agnostic in `commonMain`

### Coroutines & Threading
- Use `Dispatchers.IO` for IO-bound operations
- Use `Dispatchers.Default` for CPU-bound operations  
- Main dispatcher is set appropriately per platform
- Repositories should handle their own context switching
- Never block the main thread

## UI/UX Guidelines

### Responsive Design
- Use `GridCells.Adaptive` for responsive grids
- Support different screen sizes and orientations
- Use `WindowInsets` for proper padding (safe area, keyboard)
- Test on both phones and tablets

### State Management
- Always show loading states during async operations
- Provide error states with retry actions
- Show empty states with helpful messages
- Persist critical state across configuration changes

### Animations & Transitions
- Use `AnimatedContent` for content transitions
- Apply `AnimatedVisibility` for show/hide animations
- Keep animations smooth (60fps) and purposeful
- Avoid excessive animations that distract users

### Accessibility
- Provide content descriptions for images
- Use semantic properties for screen readers
- Ensure sufficient color contrast
- Support dynamic text sizing

## Testing Standards

### Unit Tests
- Test ViewModels thoroughly with different state scenarios
- Test repositories with mocked dependencies
- Test use cases/business logic in isolation
- Aim for 60-70% code coverage for business logic
- Use **MockK** for mocking in Kotlin tests

### Test Structure
- Follow Given-When-Then pattern
- Test failure scenarios, not just happy paths
- Use descriptive test names: `should_X_when_Y`
- Keep tests independent and isolated

### Testing Libraries
- **JUnit** for test framework
- **MockK** for mocking
- **Turbine** for testing Flows
- **Compose Testing** for UI tests

## Build & Configuration

### Gradle
- Use **version catalog** (`libs.versions.toml`) for all dependencies
- Keep dependency versions up-to-date
- Minimize dependencies - only include what's necessary
- Document reason for each major dependency

### Build Configuration
- Maintain separate `debug` and `release` build types
- Configure ProGuard/R8 rules for release builds
- Use build variants only when truly needed
- Keep Gradle files clean and organized

### Version Management
- Semantic versioning for app: `major.minor.patch`
- Increment `versionCode` for each release
- Keep `versionName` in sync with git tags

## Performance & Optimization

### General Performance
- Avoid memory leaks - cancel jobs, clear observers
- Use efficient data structures (prefer `List` over `Set` when order matters)
- Profile before optimizing - don't guess
- Minimize allocations in hot paths

### Image Loading
- Use **Coil 3** for image loading (already configured)
- Always specify content scale and placeholder
- Cache images appropriately
- Resize images to display size

### Compose Performance
- Use `remember` to avoid recomputations
- Apply `@Stable` and `@Immutable` to data passed to composables
- Use `key()` in lazy lists for item stability
- Avoid lambda allocations in composables when possible

## Git & Version Control

### .gitignore
- Exclude build artifacts (`**/build/`, `.gradle`)
- Exclude IDE files (`.idea`, `*.iml`, `.DS_Store`)
- Exclude platform-specific user data (`xcuserdata`, `local.properties`)
- Never commit secrets or API keys

### Commit Messages
- Use conventional commits: `type(scope): description`
- Types: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`
- Keep first line under 72 characters
- Provide context in commit body when needed

## Code Review Checklist

Before submitting code, ensure:
- [ ] Code follows MVVM and Clean Architecture patterns
- [ ] No business logic in UI layer
- [ ] Proper error handling throughout
- [ ] State management uses StateFlow with lifecycle awareness
- [ ] No memory leaks (coroutines cancelled, observers cleared)
- [ ] UI handles loading, error, and empty states
- [ ] Nullable types handled safely (no `!!` operator)
- [ ] Dependencies injected via constructor
- [ ] Tests added for business logic
- [ ] Code is readable with meaningful names
- [ ] No code smells (god classes, long methods, duplication)
- [ ] Gradle version catalog updated if dependencies added
- [ ] Documentation updated if architecture changes

## Common Patterns

### ViewModel Pattern
```kotlin
class MyViewModel(
    private val repository: MyRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getData()
                .catch { _state.value = UiState.Error(it.message) }
                .collect { _state.value = UiState.Success(it) }
        }
    }
}
```

### Screen Pattern
```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    when (state) {
        is UiState.Loading -> LoadingContent()
        is UiState.Error -> ErrorContent(state.message)
        is UiState.Success -> SuccessContent(state.data)
    }
}
```

### Repository Pattern
```kotlin
class MyRepository(
    private val api: MyApi,
    private val storage: MyStorage
) {
    fun getData(): Flow<List<Item>> = flow {
        // Try cache first
        storage.getItems().firstOrNull()?.let { emit(it) }
        
        // Fetch from network
        val items = api.fetchItems()
        storage.saveItems(items)
        emit(items)
    }
}
```

## Package Naming Convention

- **Package**: `com.mindera.alfie`
- **iOS Bundle ID**: `com.mindera.alfie`
- **App Name**: "Alfie KMP" (Android), "Alfie" (iOS)

Always maintain consistency across platforms.

---

**Remember**: Write code that is clean, testable, and maintainable. When in doubt, favor clarity over cleverness.
