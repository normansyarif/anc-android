package ac.id.unja.anc.Volley;

import java.text.DecimalFormat;

public class Tool {

    public Tool(){}

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(Integer.valueOf(amount)).replace(",", ".");
    }

    public static String getStatus(String booleanStatus) {
        String status = booleanStatus;
        if (status.equals("0")) status = "Menunggu Persetujuan Ketua RT";
        else if (status.equals("1")) status = "Menunggu Proses Kelurahan";
        else if (status.equals("2")) status = "Surat Dapat Diambil di Kelurahan";
        else if (status.equals("3")) status = "Selesai";
        else status = "Dibatalkan";

        return status;
    }

    public static String getStatusMenikah(String booleanStatus) {
        String status = booleanStatus;
        if (status.equals("0")) status = "Belum Menikah";
        else if (status.equals("1")) status = "Menikah";
        else status = "Bercerai";

        return status;
    }

    public static String getJenisKelamin(String booleanStatus) {
        String status = booleanStatus;
        if (status.equals("0")) status = "Perempuan";
        else status = "Laki-laki";

        return status;
    }

    public static String getSurat(String jenis_surat) {
        String judul = "Tidak Mampu";
        if(jenis_surat.equals("2")) judul = "Domisili";
        else if(jenis_surat.equals("3")) judul = "Bersih Diri";
        else if(jenis_surat.equals("4")) judul = "Rekomendasi";
        else if(jenis_surat.equals("5")) judul = "Umum";
        else if(jenis_surat.equals("6")) judul = "Kematian";

        return judul;
    }
}
