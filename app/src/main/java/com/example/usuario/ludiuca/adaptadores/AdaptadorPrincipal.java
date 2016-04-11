package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoClases;
import com.example.usuario.ludiuca.fragmentos.FragmentoLogin;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;

public class AdaptadorPrincipal extends FragmentPagerAdapter {
    Context c;

    public AdaptadorPrincipal(FragmentManager fm, Context ctx) {
        super(fm);
        c = ctx;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentoPerfil();
            case 1:
                return new FragmentoClases();
            case 2:
                return new FragmentoLogin();
        }
        return null;
    }

    public Fragment get(int p) {
        return getItem(p);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Perfil";
            case 1:
                return "Clases";
            case 2:
                return "Notificaciones";
        }
        return "";
    }

    @Override
    public int getCount() {
        return 3;
    }

}