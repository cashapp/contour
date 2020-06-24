# Change Log

## Version 0.1.8

_2020-06-11_

  * Change: Renames `applyLayout()` to `layoutBy()`.
  * New: Adds a `layoutBy {}` extension function for safely laying out nested contour views. This is
    useful because Kotlin's `apply {}` extension makes it extremely easy to call `layoutBy()` on the
    wrong scope, resulting in a `StackOverflowException`.

    ```kotlin
    class NoteView(context: Context) : ContourLayout(context) {
      val name = TextView(context).layoutBy {
        text = "Ben Sisko"
        textSize = 16.sp
        LayoutSpec(
          x = leftTo { parent.left() },
          y = topTo { parent.top() }
        )
      }
    }
    ```

## Version 0.1.7

_2020-05-19_

 * New: Adds `compareTo` operations to contour primitives.
