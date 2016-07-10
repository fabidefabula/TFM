package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoCambiarAvatar;
import com.example.usuario.ludiuca.fragmentos.FragmentoClases;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;

public class AdaptadorCambiarAvatarProfe extends FragmentPagerAdapter {
    Context c;

    public AdaptadorCambiarAvatarProfe(FragmentManager fm, Context ctx) {
        super(fm);
        c = ctx;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentoCambiarAvatar();

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
                return "Elige nuevo avatar";

        }
        return "";
    }

    @Override
    public int getCount() {
        return 1;
    }

}