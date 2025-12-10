# MovieShorts - Movie DB App

Offline First Android App to show list of movie trending and now playing , detail screen , list screen , movie can be searched , shared and bookmarked.

<img width="200" height="400" alt="Screenshot 2025-12-10 at 1 16 30 PM" src="https://github.com/user-attachments/assets/a4282bdc-27be-4b7e-ba98-f1d295d12206" />

<img width="200" height="400" alt="Screenshot 2025-12-10 at 1 17 29 PM" src="https://github.com/user-attachments/assets/e03137a0-58c6-4c05-8c09-7df271dd4b5d" />

<img width="200" height="400" alt="Screenshot 2025-12-10 at 1 17 42 PM" src="https://github.com/user-attachments/assets/a4ad60e4-9a1c-4869-8a65-aaf512dc3499" />

<img width="200" height="400" alt="Screenshot 2025-12-10 at 1 19 41 PM" src="https://github.com/user-attachments/assets/f6c84b14-c2bb-410a-a184-b435999a2077" />


## 🏗️ Architecture

**MVVM + Clean Architecture**

```
Presentation → ViewModel → Domain → Data
```

## 🛠️ Tech Stack

- Kotlin, XML
- Coroutines & Flow
- Room, Paging3, Dagger
- Min SDK: 24 | Target SDK: 35

## 🚀 Getting Started
1. Clone repository
2. Open in Android Studio
3. Sync Gradle
4. Run app

```bash
./gradlew assembleDebug  # Build
./gradlew test          # Test
```

## 🌿 Branching Strategy

**Rules:**
- ✅ Always checkout from `development` branch
- ✅ Create feature branches: `feature/feature-name`
- ❌ Never push to `main` (protected)

**Workflow:**
```bash
git checkout development
git pull origin development
git checkout -b feature/your-feature-name
# Make changes, commit, push
git push origin feature/your-feature-name
# Create PR to development
```

## 🎯 MVVM Best Practices

- **View**: UI only (XML Screens)
- **ViewModel**: Business logic, state management (StateFlow/LiveData)
- **Repository**: Single source of truth
- Use `viewModelScope` for coroutines
- Unit test ViewModels

## 🤝 Contributing

1. Follow MVVM architecture
2. Create feature branch from `development`
3. Write clean, tested code
4. Follow code style guidelines

---

