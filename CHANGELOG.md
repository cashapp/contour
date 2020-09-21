# Change Log

## 1.0.0 - _2020-09-21_

- Rename `applyLayout()` to `layoutBy()`.
- Add utility axis solvers:
  - [`contourWidthMatchParent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L307) and [`contourHeightMatchParent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L336)
  - [`contourWidthWrapContent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L328) and [`contourHeightWrapContent()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L357)
  - [`matchXTo()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L564) and [`matchYTo()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L592)
  - [`matchParentX()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L578) and [`matchParentY()`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L606)

### Breaking changes (for 0.1.7)
- Add support for padding. This can be disabled on a per-View basis using [`respectPadding = false`](https://github.com/cashapp/contour/blob/e45ab4a625dbf37ec419d864ef7692c3c7bb01c7/contour/src/main/kotlin/com/squareup/contour/ContourLayout.kt#L176).
- Remove `onInitializeLayout()` in favor of laying Views in `init`.

## 0.1.7 - _2020-05-19_

- Add `compareTo` operations to contour primitives.
