# Abyss Rift Rune Highlighter

A passive RuneLite scene overlay that shows the matching rune icon above every
exit rift in the Abyss. Optional rune names make similar-looking symbols easier
to distinguish at a glance.

## Features

- Labels all 13 Abyss exits: air, blood, body, chaos, cosmic, death, earth,
  fire, law, mind, nature, soul and water.
- Uses RuneLite gameval object and item constants rather than hard-coded rift
  IDs.
- Tracks decorative-object spawn events instead of scanning the scene while
  rendering.
- Lets you change icon size, vertical placement, label visibility and label
  colour.
- Works alongside RuneLite's built-in Runecraft clickbox and minimap overlays.

This plugin only draws passive overlays. It does not add menu actions, inject
input, send network requests, or interact with the game automatically.

## Configuration

- **Show rune names** — displays the rune name beneath its icon.
- **Icon size** — scales world-space rune icons from 20 to 64 pixels.
- **Height offset** — moves the icon stack vertically above each rift.
- **Name colour** — changes the optional label colour.

## Development

Java 11 is required.

### IntelliJ IDEA

1. Choose **File → Open** and select this repository's `build.gradle`.
2. Import it as a Gradle project and use the checked-in Gradle wrapper.
3. Set the Gradle JVM to an installed Java 11 or newer JDK; compilation is
   pinned to Java 11 bytecode by the build.
4. Select **Abyss Rift Rune Highlighter** in IntelliJ's top-right Run/Debug
   selector and click the green Run button. The same launcher is available at
   **Gradle → Tasks → other → run**.

The `run` task starts RuneLite in developer mode and loads
`AbyssRiftRuneHighlighterPlugin` as an external plugin. No committed `.idea`
files or local absolute paths are required; the shared launcher lives in
`.run/Abyss Rift Rune Highlighter.run.xml`.

Build and run the unit tests:

```powershell
.\gradlew clean build
```

Launch the RuneLite development client:

```powershell
.\gradlew run
```

For Jagex Accounts, follow RuneLite's
[development-client login instructions](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

In-game behavior must be confirmed manually inside the Abyss. Check every
rift, rotate the camera, vary zoom, and try the smallest and largest icon sizes.

## License

This project is licensed under the BSD 2-Clause License. See [LICENSE](LICENSE).
