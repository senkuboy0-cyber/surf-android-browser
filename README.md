# Surf Android Browser

A modern, privacy-focused Android browser built with Kotlin and GeckoView (Mozilla's browser engine). 

## Features

### 🌐 Core Browser Features
- **WebEngine**: GeckoView for rendering web content
- **Navigation**: Back, Forward, Refresh, Home buttons
- **Multi-Tab Support**: Tab management with indicators
- **Bookmarks**: Save and organize favorite websites
- **History**: Track browsing history with search
- **Downloads**: Built-in download manager
- **URL Bar**: Search suggestions and autocompletion

### 🔒 Privacy & Security
- **Private Mode**: Incognito browsing
- **Ad Blocking**: Built-in ad blocker powered by uBlock Origin
- **HTTPS Indicator**: Secure connection warnings
- **Safe Browsing**: Protection against malicious sites
- **Cookie Controls**: Manage cookie permissions
- **Location Tracking**: Control location access

### 🎨 User Experience
- **Dark/Light Theme**: System theme support
- **Reading Mode**: Clean article view
- **Text-to-Speech**: Read articles aloud
- **Share Functionality**: Share links to other apps
- **Gestures**: Swipe navigation
- **Progress Bar**: Page loading indicator

### 🚀 Advanced Features
- **Extension Support**: Firefox extensions via GeckoView
- **Cross-Device Sync**: Bookmarks and history sync
- **PWA Support**: Progressive Web Apps
- **QR Code Scanner**: Scan URLs
- **Biometric Lock**: Fingerprint/face unlock
- **Data Usage Tracker**: Monitor data consumption

### 📱 Extension Store
- **Mozilla Extensions API**: Browse Firefox extensions
- **Install/Uninstall**: One-click extension management
- **Extension Reviews**: User ratings and feedback
- **Pre-installed Extensions**: 
  - uBlock Origin (Ad blocking)
  - Dark Reader
  - Privacy Badger
  - HTTPS Everywhere

## Requirements

- **Android**: minimum SDK 24 (Android 7.0)
- **Kotlin**: 1.9.22
- **GeckoView**: 147.0.20260212191108

## Installation

1. Clone the repository:
```bash
git clone https://github.com/senkuboy0-cyber/surf-android-browser.git
```

2. Open in Android Studio
3. Sync Gradle files
4. Build and run

## Architecture

### Components
- **MainActivity**: Main browser interface
- **TabManager**: Multi-tab management
- **BrowserViewModel**: MVVM pattern implementation
- **GeckoView**: Web content rendering
- **Room Database**: Local data storage
- **Retrofit**: API calls for extensions

### Database
- **BookmarkDatabase**: Store bookmarks
- **HistoryDatabase**: Store browsing history
- **PreferenceManager**: User preferences

### API Integration
- **Mozilla Add-ons API**: Extension management
- **GeckoView WebExtensions**: Extension execution

## Configuration

### GeckoView Settings
- JavaScript enabled
- DOM storage enabled
- File access allowed

### Privacy Settings
- Default ad blocking enabled
- Private mode available
- Location tracking controlled

### Theme Support
- Light theme (default)
- Dark theme
- System theme dependent

## Dependencies

Key dependencies include:
- **GeckoView**: org.mozilla.geckoview:geckoview
- **Room Database**: for local storage
- **Retrofit**: for API calls
- **Navigation Component**: for navigation
- **Material Design**: for UI components
- **Coroutines**: for async operations

## Permissions

Required permissions:
- `INTERNET`: For web browsing
- `ACCESS_NETWORK_STATE`: Network detection
- `ACCESS_FINE_LOCATION`: Location-based features
- `CAMERA`: QR code scanning
- `WRITE_EXTERNAL_STORAGE`: File downloads
- `USE_BIOMETRIC`: Biometric authentication

## Browser Features by Category

### Privacy & Security
- ✅ Private browsing mode
- ✅ Ad blocking
- ✅ Secure browsing indicators
- ✅ Cookie management
- ✅ Location controls

### User Interface
- ✅ Material Design 3
- ✅ Dark/Light themes
- ✅ Bottom navigation
- ✅ Gesture support
- ✅ Responsive design

### Extensions
- ✅ Firefox extensions support
- ✅ Extension store integration
- ✅ Extension management
- ✅ Permission controls

### Performance
- ✅ Data saver mode
- ✅ Battery optimization
- ✅ Memory management
- ✅ Background downloads

## Future Enhancements

Planned features:
- [ ] AI-powered content suggestions
- [ ] Voice search integration
- [ ] Password manager
- [ ] Reading list sync
- [ ] Custom extension API
- [ ] Performance profiling

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- **Mozilla** for GeckoView browser engine
- **Google** for Material Design components
- **Open Source Community** for various libraries

## Contact

For support and inquiries:
- GitHub Issues: [Create an issue](https://github.com/senkuboy0-cyber/surf-android-browser/issues)
- Email: 

---

**Surf Browser** - The modern, privacy-focused Android browser with extension support.
