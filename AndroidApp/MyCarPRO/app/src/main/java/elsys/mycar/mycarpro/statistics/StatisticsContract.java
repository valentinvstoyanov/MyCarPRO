package elsys.mycar.mycarpro.statistics;

import elsys.mycar.mycarpro.base.BasePresenter;
import elsys.mycar.mycarpro.base.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void onVehicleChanged(String vehicleId);

    }
}
