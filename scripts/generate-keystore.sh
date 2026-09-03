#!/usr/bin/env bash
set -euo pipefail

KEYSTORE="${1:-spider-ai-release.jks}"
ALIAS="${2:-spider-ai}"

if [[ -e "$KEYSTORE" ]]; then
  echo "Refusing to overwrite existing keystore: $KEYSTORE"
  exit 1
fi

keytool -genkeypair \
  -v \
  -keystore "$KEYSTORE" \
  -alias "$ALIAS" \
  -keyalg RSA \
  -keysize 4096 \
  -validity 10000

echo
echo "Created $KEYSTORE"
echo "BACK IT UP SECURELY. Do not commit it to Git."
echo "For GitHub Actions, encode it with:"
echo "  base64 -w 0 $KEYSTORE > spider-ai-release.base64"
