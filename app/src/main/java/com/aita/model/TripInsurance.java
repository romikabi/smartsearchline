package com.aita.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.aita.shared.AitaContract.TripInsuranceEntry;

public class TripInsurance implements Parcelable {

    public static final Parcelable.Creator<TripInsurance> CREATOR = new Parcelable.Creator<TripInsurance>() {
        @Override
        public TripInsurance createFromParcel(Parcel source) {
            return new TripInsurance(source);
        }

        @Override
        public TripInsurance[] newArray(int size) {
            return new TripInsurance[size];
        }
    };
    private final String source;
    private final String sourceId;
    private final String series;
    private final String documentUrl;
    private final String paymentId;
    private final String policyId;

    public TripInsurance(@NonNull Cursor cursor) {
        int sourceColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_SOURCE);
        int sourceIdColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_SOURCE_ID);
        int seriesColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_SERIES);
        int documentUrlColId = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_DOCUMENT_URL);
        int paymentIdColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_PAYMENT_ID);
        int policyIdColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_POLICY_ID);
        int productJsonColInd = cursor.getColumnIndex(TripInsuranceEntry.COLUMN_PRODUCT_JSON);

        this.source = cursor.getString(sourceColInd);
        this.sourceId = cursor.getString(sourceIdColInd);
        this.series = cursor.getString(seriesColInd);
        this.documentUrl = cursor.getString(documentUrlColId);
        this.paymentId = cursor.getString(paymentIdColInd);
        this.policyId = cursor.getString(policyIdColInd);
    }

    @SuppressWarnings("WeakerAccess")
    protected TripInsurance(Parcel in) {
        this.source = in.readString();
        this.sourceId = in.readString();
        this.series = in.readString();
        this.documentUrl = in.readString();
        this.paymentId = in.readString();
        this.policyId = in.readString();
    }

    @NonNull
    public static String fakeJsonString() {
        return "{\"source\":\"test_source\",\"source_id\":\"test_source_id\",\"series\":\"test_series\",\"document_url\":\"test_document_url\",\"payment_id\":\"test_payment_id\",\"policy_id\":\"test_policy_id\",\"product\":{\"calculation\":{\"currency\":\"RUB\",\"price\":250},\"rules\":\"https://www.alfastrah.ru/docs/Offer_AITA.pdf\",\"product\":{\"default\":true,\"code\":\"APPINTHEAIR-NS-BG-ZR\",\"description\":\"APPINTHEAIR NS BG ZR product\",\"name\":{\"ru\":\"Страхование на время перелёта\",\"en\":\"Inflight insurance\"}}}}";
    }

    @NonNull
    public String getSource() {
        return source;
    }

    @NonNull
    public String getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getSeries() {
        return series;
    }

    @NonNull
    public String getDocumentUrl() {
        return documentUrl;
    }

    @NonNull
    public String getPaymentId() {
        return paymentId;
    }

    @NonNull
    public String getPolicyId() {
        return policyId;
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + sourceId.hashCode();
        result = 31 * result + series.hashCode();
        result = 31 * result + documentUrl.hashCode();
        result = 31 * result + paymentId.hashCode();
        result = 31 * result + policyId.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source);
        dest.writeString(this.sourceId);
        dest.writeString(this.series);
        dest.writeString(this.documentUrl);
        dest.writeString(this.paymentId);
        dest.writeString(this.policyId);
    }

}