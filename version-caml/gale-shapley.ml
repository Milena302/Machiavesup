type proposant = P of int
type disposant = D of int
type instance = {
  appelle_suivant: proposant -> disposant option; (* TODO  proposant -> disposant list *)
  prefere: disposant -> proposant -> proposant -> bool;
  proposants: proposant list;
  clone: unit -> instance
}


let rec run_gs_aux instance proposants_celibataires couplage_partiel = 
  match proposants_celibataires with
  | [] -> couplage_partiel
  | p1 :: props ->
    match instance.appelle_suivant p1 with
    | None -> run_gs_aux instance props couplage_partiel
    | Some d1 ->
      match List.assoc_opt d1 couplage_partiel with
      | None -> (* d1 est celibataire *)
        run_gs_aux instance  props ((d1,p1)::couplage_partiel)
      | Some p2 -> (* d1 est fiance à p2 *)
        let (p,p') = if instance.prefere d1 p1 p2 then (p1, p2) else (p2, p1) in
        run_gs_aux instance (p'::props) ((d1,p)::couplage_partiel)

let run_gs instance : (disposant * proposant ) list =
  run_gs_aux instance instance.proposants []

let run_gs_non_det disposant0 instance : (disposant * proposant ) list list =
  (* renvoie tous les couplages stables possibles selon la liste de disposant0 *) 
  let rec iter proposants_celibataires couplage_partiel meilleur_rejete =
    (* meilleur rejete = le proposant le mieux classe parmis ceux qui ont fait une 
       proposition à disponsant0 depuis le debut (mais pas acceptee) *)
    match proposants_celibataires with
    | [] -> [couplage_partiel]
    | p1::props ->
      match instance.appelle_suivant p1 with
      | None -> iter props couplage_partiel meilleur_rejete
      | Some d1 ->
      if d1 = disposant0 then begin
        let p1_a_sa_chance, meilleur_rejete2 = match meilleur_rejete with
          | None -> true, Some p1
          | Some p2 when instance.prefere d1 p1 p2 -> true, Some p1
          | _ -> false, meilleur_rejete in
        let inst2 = instance.clone () in
        (* branche 1 : d0 rejete la proposition de p1 *)
        (iter (p1::props) couplage_partiel meilleur_rejete2) @ 
        (* branche 2 : d1 accepte la proposition de p1 *)
        (if not p1_a_sa_chance then [] else [run_gs_aux inst2 proposants_celibataires ((d1,p1)::couplage_partiel)])        
      end else match List.assoc_opt d1 couplage_partiel with
      | None -> (* d1 est celibataire *)
        iter props ((d1,p1)::couplage_partiel) meilleur_rejete
      | Some p2 -> (* d1 est fiance à p2 *)
        let (p,p') = if instance.prefere d1 p1 p2 then (p1, p2) else (p2, p1) in
        iter (p'::props) ((d1,p)::couplage_partiel) meilleur_rejete
    in iter instance.proposants [] None

let index a x = 
  let rec iter res = 
    if res = Array.length a then raise Not_found
    else if a.(res) = x then res
    else iter (res+1)
  in iter 0

let instance_of_matrices m1 m2 =
  let nb_props = Array.length m1 in 
  let proposants = List.init nb_props (fun i -> P i) in
  let prefere (D i) (P j) (P k) = 
    let l = m2.(i) in
    index l j < index l k in
  let rec gen rang_appel =
    let rang_appel = Array.copy rang_appel in
    let appelle_suivant (P i) = 
      let r = rang_appel.(i) in
      if r < Array.length m1.(i) then begin
        rang_appel.(i) <- r + 1;
        Some (D (m1.(i).(r)))
      end else  None in
      {proposants;prefere;appelle_suivant;clone} in
    {proposants;prefere;appelle_suivant;clone}

let m1 = [|
  [| 0; 1|];
  [| 1; 0|]
|]

let m2 = [|
  [| 1; 0 |];
  [| 0; 1|]
|]

let gs_resultat = run_gs (instance_of_matrices m1 m2)
let gs_all = run_gs_non_det (D 0) (instance_of_matrices m1 m2)
