package dev.kreienbuehl.mapcomposekmp.ui

import cafe.adriel.voyager.core.screen.Screen
import dev.kreienbuehl.mapcomposekmp.ui.screens.AddingMarkerDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.AnimationDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.CalloutDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.CenteringOnMarkerDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.CustomDraw
import dev.kreienbuehl.mapcomposekmp.ui.screens.HttpTilesDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.LayersDemoSimple
import dev.kreienbuehl.mapcomposekmp.ui.screens.MapDemoSimple
import dev.kreienbuehl.mapcomposekmp.ui.screens.MarkersClusteringDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.MarkersLazyLoadingDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.OsmDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.PathsDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.RotationDemo
import dev.kreienbuehl.mapcomposekmp.ui.screens.VisibleAreaPaddingDemo

const val HOME = "home"

enum class MainDestinations() {
    MAP_ALONE {
        override val title = "Simple map"
        override val screen = MapDemoSimple
    },
    LAYERS_DEMO {
        override val title = "Layers demo"
        override val screen = LayersDemoSimple
    },
    MAP_WITH_ROTATION_CONTROLS {
        override val title = "Map with rotation controls"
        override val screen = RotationDemo
    },
    ADDING_MARKERS{
        override val title = "Adding markers"
        override val screen = AddingMarkerDemo
    },
    CENTERING_ON_MARKER{
        override val title = "Centering on marker"
        override val screen = CenteringOnMarkerDemo
    },
    PATHS{
        override val title = "Map with paths"
        override val screen = PathsDemo
    },
    CUSTOM_DRAW{
        override val title = "Map with custom drawings"
        override val screen = CustomDraw
    },
    CALLOUT_DEMO{
        override val title = "Callout (tap markers)"
        override val screen = CalloutDemo
    },
    ANIMATION_DEMO{
        override val title = "Animation demo"
        override val screen = AnimationDemo
    },
    OSM_DEMO{
        override val title = "Open Street Map demo"
        override val screen = OsmDemo
    },
    HTTP_TILES_DEMO{
        override val title = "Remote HTTP tiles"
        override val screen = HttpTilesDemo
    },
    VISIBLE_AREA_PADDING{
        override val title = "Visible area padding"
        override val screen = VisibleAreaPaddingDemo
    },
    MARKERS_CLUSTERING{
        override val title = "Markers clustering"
        override val screen = MarkersClusteringDemo
    },
    MARKERS_LAZY_LOADING{
        override val title = "Markers lazy loading"
        override val screen = MarkersLazyLoadingDemo
    };

    abstract val title: String
    abstract val screen: Screen
}
