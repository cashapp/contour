# Contour
Contour is a typesafe, Kotlin first API for complex layouts on Android.

## Example
Let's create a simple note taking view that displays a username aligned to the left, and fills the remaining horizontal space with the note.

```kotlin
class NoteView(context: Context) : ContourLayout(context) {
  private val name: TextView =
    TextView(context).apply {
      text = "Ben Sisko"
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
      layoutOf(
        leftTo { parent.left() + 15.dip },
        topTo { parent.top() + 15.dip }
      )
    }

  private val note =
    TextView(context).apply {
      text = siskoWisdom
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
      layoutOf(
        leftTo {
          name.right() + 15.dip
        }.rightTo {
          parent.right() - 15.dip
        },
        topTo {
          parent.top() + 15.dip
        }
      )
    }
}
```

We should also set the view height to match the `note: TextView` height plus some padding.

```kotlin
init {
  heightOf { description.bottom() + 15.dip }
}
```

Let's also introduce an avatar, and have it's width and height match the width of the `name: TextView`.

```kotlin
  private val avatar =
    AvatarImageView(context).apply {
      scaleType = ImageView.ScaleType.CENTER_CROP
      Picasso.get()
        .load("https://upload.wikimedia.org/wikipedia/en/9/92/BenSisko.jpg")
        .into(this)
      layoutOf(
        leftTo {
          name.left()
        }.widthOf {
          name.width()
        },
        topTo {
          name.bottom()
        }.heightOf {
          name.width().toY()
        }
      )
    }
```

Finally, let's insert a created date between the note content, and the bottom of the view. If there is not enough content in the `note: TextView`, let's alrign the created date vertically with the name & icon.

```kotlin
  private val starDate = TextView(context).apply {
    text = "Stardate: 23634.1"
    setTextColor(White)
    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
    layoutOf(
      rightTo { parent.right() - 15.dip },
      maxOf(
        topTo { note.bottom() + 5.dip },
        bottomTo { name.bottom() }
      )
    )
  }
```

What does the end result of this look like?

<p align="center">
  <img width="460" src="screenshots/simple_demo.gif">
</p>

## Features
### Functional API
Similar to `ConstraintLayout`, Contour works by creating relationships between views position and size. The differnce is instead of providing opaque XML attributes, you instead provide functions which are directly called during the layout phase.
This offers a couple advantages:
- Axis to Kotlin math operators (`parent.right() - 15.dip` is completely valid)
- Ability to make runtime layout decisions. 
```
leftTo { 
  if (user.name.isEmpty) parent.left()
  else nameView.right()
}
```
- Clean minimal sytax
- Additional type safety


### Axis Type Saftey
Contour makes heavy use of inline classes to provide axis type saftey in layouts. What this means is `toLeftOf { view.top() }` will not compile. `toLeftOf {}` requires a `XInt`, and `top()` returns a `YInt`. In cases where this needs to be forced, casting functions are made available to `toY()` & `toX()`. 

Inline classes are a lightweight compile time addition that allow this feature with minimal to no performance costs. 
https://kotlinlang.org/docs/reference/inline-classes.html
