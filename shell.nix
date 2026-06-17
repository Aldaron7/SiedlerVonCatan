{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk17
    maven
    gtk3
    glib
    libGL
    xorg.libXxf86vm
  ];

  shellHook = ''
    export LD_LIBRARY_PATH=${with pkgs; pkgs.lib.makeLibraryPath [
      glib
      gtk3
      libGL
      xorg.libXxf86vm
    ]}:$LD_LIBRARY_PATH
  '';
}
