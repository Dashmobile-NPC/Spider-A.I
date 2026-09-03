# Spider-AI

A release-ready Android starter for **SPIDER-AI**, an original resourceful AI-assistant persona. The app is designed to support connected and offline-capable operation while respecting Android permissions and security boundaries.

## Release pipeline

Publishing a GitHub Release such as `v1.0.0`, or manually running the workflow from GitHub Actions, triggers:

1. GitHub Actions checks out that release tag.
2. Java 17, Android SDK, and Gradle 8.9 are configured.
3. The signing keystore is reconstructed from GitHub Secrets in the runner's temporary directory.
4. Gradle builds a signed `release` APK.
5. A SHA-256 checksum is generated.
6. Both files are attached to the GitHub Release.

AGP 8.7 requires Gradle 8.9 and JDK 17, so this project pins that compatible toolchain. See Android's compatibility table: https://developer.android.com/build/releases/about-agp

## GitHub signing setup

Create four repository Actions secrets under **Settings → Secrets and variables → Actions**:

- `ANDROID_KEYSTORE_BASE64`
- `ANDROID_KEYSTORE_PASSWORD`
- `ANDROID_KEY_ALIAS`
- `ANDROID_KEY_PASSWORD`

Generate a keystore with `scripts/generate-keystore.sh` or the command documented there. Never commit the `.jks` file or passwords.

## Create a release

From GitHub, create and publish a release with a tag such as:

`v1.0.0`

The workflow `.github/workflows/ci.yml` will build and attach. You can start it manually from **Actions → Build and Release Spider-AI APK → Run workflow**:

- `Spider-AI-v1.0.0.apk`
- `Spider-AI-v1.0.0.sha256`

## Local development

Open the project in Android Studio and let Gradle sync. For local testing, build the debug variant. The production signing key is intentionally not included in this repository.

## Important limitation

The app does not have unrestricted control over Android or other applications. Android permission prompts, OS security, app sandboxing, and user authorization remain in force.
