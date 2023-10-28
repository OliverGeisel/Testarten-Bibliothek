/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.tdd;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class TelefonbuchService {
	private final Telefonbuch telefonbuch;

	public TelefonbuchService (Telefonbuch telefonbuch) {
		this.telefonbuch = telefonbuch;
	}

	public TelefonbuchEintrag speichern (TelefonbuchEintrag eintrag) {
		return telefonbuch.save(eintrag);
	}

	public Long anzahl () {
		return telefonbuch.count();
	}

	public void loeschen (TelefonbuchEintrag eintrag) {
		telefonbuch.delete(eintrag);
	}

	public void loeschen (UUID id) {
		telefonbuch.deleteById(id);
	}

	public Streamable<TelefonbuchEintrag> alleEintraege () {
		return telefonbuch.findAll();
	}

	public Streamable<TelefonbuchEintrag> findeMitNachnamen (String suchbegriff) {
		return telefonbuch.findByNachnameAllIgnoreCase(suchbegriff);
	}
}

