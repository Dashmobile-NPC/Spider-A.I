# Spider-AI release checklist

## One-time setup

- [ ] Generate `spider-ai-release.jks`.
- [ ] Back up the keystore securely.
- [ ] Convert it to Base64.
- [ ] Add the four GitHub Actions Secrets.
- [ ] Confirm `.gitignore` excludes signing material.

## Every release

- [ ] Increment `versionCode` in `app/build.gradle.kts`.
- [ ] Update `versionName`.
- [ ] Commit and push to `main`.
- [ ] Create a GitHub Release with a tag such as `v1.0.0`.
- [ ] Publish the release.
- [ ] Wait for `Build and Release Spider-AI APK` to finish.
- [ ] Download the APK from the release assets.
- [ ] Keep the SHA-256 checksum with the release.
