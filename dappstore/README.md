dApp Store Publishing
---

> See the full guide [here](https://docs.solanamobile.com/dapp-publishing/setup).

## Initial Setup

`corepack enable`

`corepack prepare pnpm@`npm info pnpm --json | jq -r .version` --activate`

`cd publishing`

`pnpm init`

`pnpm install --save-dev @solana-mobile/dapp-store-cli`

> Do not forget to put the publisher `keypair.json` file in the /publishing.

## Validate Publishing Configuration

`npx dapp-store validate -k keypair.json -b <path_to_your_android_sdk_build_tools>`

> You can omit the `-b` flag if your `ANDROID_TOOLS_DIR` is the same as in the `publishing/.env`

## Mint your NFTs

> See the full guide [here](https://docs.solanamobile.com/dapp-publishing/publisher-and-app-nft).

### Publisher NFT

`npx dapp-store create publisher -k keypair.json [-u <mainnet_beta_rpc_url>]`

### App NFT

`npx dapp-store create app -k keypair.json [-u <mainnet_beta_rpc_url>]`

### Submit Release

> See the full guide [here](https://docs.solanamobile.com/dapp-publishing/submit).

`npx dapp-store create release -k keypair.json -b <path_to_your_android_sdk_build_tools> [-u <mainnet_beta_rpc_url>]`

`npx dapp-store publish submit -k keypair.json -u <mainnet_beta_rpc_url> --requestor-is-authorized --complies-with-solana-dapp-store-policies`