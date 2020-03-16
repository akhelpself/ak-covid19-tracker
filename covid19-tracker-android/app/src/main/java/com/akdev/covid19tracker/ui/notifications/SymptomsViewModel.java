package com.akdev.covid19tracker.ui.notifications;

import android.app.Application;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import com.akdev.covid19tracker.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SymptomsViewModel extends AndroidViewModel {

    private MutableLiveData<Spanned> mTextHowItSpreadsDesc;
    private MutableLiveData<Spanned> mTextCleanYourHands;
    private MutableLiveData<Spanned> mTextVoidCloseContact;
    private MutableLiveData<Spanned> mTextStayHome;
    private MutableLiveData<Spanned> mTextCoverCough;
    private MutableLiveData<Spanned> mTextWearFaceMask;
    private MutableLiveData<Spanned> mTextCleanAndDisinfect;

    public SymptomsViewModel(@NonNull Application application) {
        super(application);
        mTextHowItSpreadsDesc = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextHowItSpreadsDesc.setValue(Html.fromHtml(application.getString(R.string.symptoms_how_it_spreads_desc), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextHowItSpreadsDesc.setValue(Html.fromHtml(application.getString(R.string.symptoms_how_it_spreads_desc)));
        }

        mTextCleanYourHands = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextCleanYourHands.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_yourself_desc_01), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextCleanYourHands.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_yourself_desc_01)));
        }

        mTextVoidCloseContact = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextVoidCloseContact.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_yourself_desc_02), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextVoidCloseContact.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_yourself_desc_02)));
        }

        mTextStayHome = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextStayHome.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_01), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextStayHome.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_01)));
        }

        mTextCoverCough = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextCoverCough.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_02), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextCoverCough.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_02)));
        }

        mTextWearFaceMask = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextWearFaceMask.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_03), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextWearFaceMask.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_03)));
        }

        mTextCleanAndDisinfect = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextCleanAndDisinfect.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_04), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextCleanAndDisinfect.setValue(Html.fromHtml(application.getString(R.string.symptoms_take_protect_others_desc_04)));
        }

    }

    public MutableLiveData<Spanned> getTextHowItSpreadsDesc() {
        return mTextHowItSpreadsDesc;
    }

    public MutableLiveData<Spanned> getTextCleanYourHands() {
        return mTextCleanYourHands;
    }

    public MutableLiveData<Spanned> getTextVoidCloseContact() {
        return mTextVoidCloseContact;
    }

    public MutableLiveData<Spanned> getTextStayHome() {
        return mTextStayHome;
    }

    public MutableLiveData<Spanned> getTextCoverCough() {
        return mTextCoverCough;
    }

    public MutableLiveData<Spanned> getTextWearFaceMask() {
        return mTextWearFaceMask;
    }

    public MutableLiveData<Spanned> getTextCleanAndDisinfect() {
        return mTextCleanAndDisinfect;
    }
}