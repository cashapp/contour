# Change Log

## 1.1.0 - _2021-03-02_

 * Fix `compareTo` methods for `YInt` and `YFloat`.
 * Avoid measure both axis when we don't have to.
 * Support for live preview in Android Studio.
 * `layoutBy()` return the view it's applied to. You now can do

   ```kotlin
   val titleText : TextView

   init {
     titleText = TextView(context).apply {
       text = "Not sure what the title is yet"
       setTextColor(0x0)
     }.layoutBy(
       leftTo { parent.left() + 16.dip },
       topTo { parent.top() + 16.dip })
   }
   ```

 * Update to Kotlin `1.4.30`.

## 1.0.0 - _2020-09-21_

 * Rename `applyLayout()` to `layoutBy()`.
 * Add utility axis solvers:
  * [`contourWidthMatchParent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L307) and [`contourHeightMatchParent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L336)
  * [`contourWidthWrapContent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L328) and [`contourHeightWrapContent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L357)
  * [`matchXTo()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L564) and [`matchYTo()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L592)
  * [`matchParentX()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L578) and [`matchParentY()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L606)

### Breaking changes (for 0.1.7)
 * Add support for padding. This can be disabled on a per-View basis using [`respectPadding = false`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L176).
 * Remove `onInitializeLayout()` in favor of laying Views in `init`.

## 0.1.7 - _2020-05-19_

 * Add `compareTo` operations to contour primitives.
