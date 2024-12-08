package backend.academy.renderers;

import backend.academy.FractalImage;
import backend.academy.transformations.Variation;
import java.util.List;

public interface Renderer {
    FractalImage render(
        FractalImage image,
        List<Variation> variations,
        int symmetry,
        int samples,
        int iters,
        int threads
    );
}
