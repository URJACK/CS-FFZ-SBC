package tw.com.celllife_f_s;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by FuFangzhou on 2017/6/3.
 */
public class CellThread extends Thread {
    public static final int SLEEP = 500;
    public boolean flag = true;
    private CellProcess cellProcess;
    private SurfaceHolder surfaceHolder;
    private DisplayView displayView;

    public CellThread(CellProcess cellProcess, DisplayView displayView) {
        this.cellProcess = cellProcess;
        this.displayView = displayView;
        this.surfaceHolder = displayView.getHolder();
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Thread.sleep(SLEEP);
                cellProcess.next();
                for (int i = 0; i < CellProcess.MAXVAR; i++) {
                    cellProcess.addVar();
                    if (i==CellProcess.MAXVAR-1)
                        cellProcess.setFullVar();
                    Canvas canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        displayView.doDraw(canvas);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    Thread.sleep(SLEEP/cellProcess.MAXVAR);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
